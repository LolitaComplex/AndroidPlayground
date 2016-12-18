package com.example.imageloader.data.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.imageloader.R;
import com.example.imageloader.data.net.DiskLruCache;
import com.example.imageloader.data.net.OkHttpUtil;
import com.example.imageloader.utils.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 1.按需读取图片
 * 2.先从内存中读，没有再从本地缓存中读，再没有网络获取
 * 3.获取完成存入本地缓存，存入内存，然后从内存读取图片返回
 */
public class ImageLoader {

    private static final String TAG = "ImageLoader";
//    private static final int TAG_IMAGE_URI = R.id.activity_image;
    private static final int IO_BUFFER_SIZE = 1024 * 8;
    private static final int DISK_CACHE_INDEX = 0;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;

    private int mWidth;
    private int mHeight;

    private Context mContext;
    private static final long mDiskCacheSize = 1024 * 1024 * 50;
    private static long maxMemory = Runtime.getRuntime().maxMemory() / 1024;
    private LruCache<String, Bitmap> mLruCache;
    private DiskLruCache mDiskLruCache;

    private ImageResizer mImageResizer = new ImageResizer();

    private String url;

    private static final ThreadFactory mThreadFactory = new ThreadFactory() {

        private int count;
        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "ImageLoader#" + count++);
        }
    };

    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(), mThreadFactory
    );

    private Handler mMainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.imageView;
            imageView.setImageBitmap(result.bitmap);
            String url = (String) imageView.getTag();
            if (url.equals(result.url)) {
                imageView.setImageBitmap(result.bitmap);
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.height = result.bitmap.getHeight();
                imageView.setLayoutParams(layoutParams);
            } else {
                Log.w(TAG, "set Image Bimap,but url has changed");
            }
        }
    };

    private static class LoaderResult{
        ImageView imageView;
        String url;
        Bitmap bitmap;

        LoaderResult(ImageView imageView, String url,
                     Bitmap bitmap) {
            this.imageView = imageView;
            this.url = url;
            this.bitmap = bitmap;
        }
    }

    public static ImageLoader from(Context context) {
        return new ImageLoader(context);
    }

    private ImageLoader(Context context) {
        this.mContext = context.getApplicationContext();//好习惯

        //初始化内存空间是进程最大内存    的1/8
        int cache = (int) (maxMemory / 2);
        //获取图片大小（KB）
        mLruCache = new LruCache<String, Bitmap>(cache) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //获取图片大小（KB）
                return value.getRowBytes() * value.getHeight() / 1024;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                Log.e(TAG, "删除了内存缓存数据" + key);
            }
        };

        //TODO
        File filesDir = mContext.getFilesDir();
        try {
            mDiskLruCache = DiskLruCache.open(filesDir, 1, 1, mDiskCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageLoader url(String url) {
        this.url = url;
        return this;
    }

    public ImageLoader size(int width, int height) {
        mWidth = width;
        mHeight = height;
        return this;
    }

    public void into(ImageView imageView) {
        bindBitmap(url, imageView, mWidth, mHeight);
    }

    /**
     * 异步方法
     */
    private void bindBitmap(final String url, final ImageView imageView, final int width, final int height) {
        imageView.setTag(url);
        Bitmap bitmapMemory = loadBitmapFromMemeoryCache(url);
        if (bitmapMemory != null) {
            imageView.setImageBitmap(bitmapMemory);
            Log.w(TAG, "It's loadBitmapFromMemeoryCache" + url);
            return;
        }

        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(url, width, height);
                if (bitmap != null) {
                    LoaderResult result = new LoaderResult(imageView, url, bitmap);
                    mMainHandler.obtainMessage(1, result).sendToTarget();
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    /**
     * 同步方法
     */
    private Bitmap loadBitmap(String url, int width, int height) {
        Bitmap bitmap = loadBitmapFromMemeoryCache(url);
        if (bitmap != null) {
            return bitmap;
        }

        try {
            bitmap = loadBitmapFromDiskCache(url, width, height);
            if (bitmap != null) {
                Log.w(TAG, "It's loadBitmapFromDiskCache" + url);
                return bitmap;
            }
            bitmap = loadBitmapFromHttp(url, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bitmap == null) {
            bitmap = downloadBitmapFromUrl(url);
        }
        return bitmap;
    }

    private Bitmap loadBitmapFromHttp(String url, int width, int height) throws IOException {
        if (mDiskLruCache == null) {
            return null;
        }

        String key = hashKeyFromUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if (downLoadUrlToStream(url, outputStream)) {
                Log.w(TAG, "It's loadBitmapFromHttp" + url);
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadBitmapFromDiskCache(url, width, height);
    }

    private Bitmap loadBitmapFromMemeoryCache(String url) {
        String key = hashKeyFromUrl(url);
        return getBitmapFromMemory(key);
    }

    private Bitmap loadBitmapFromDiskCache(String url, int width, int height) throws IOException{
        if (mDiskLruCache == null) {
            return null;
        }
        Bitmap bitmap = null;
        String key = hashKeyFromUrl(url);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (snapshot != null) {
            FileInputStream fileInputStream = (FileInputStream) snapshot
                    .getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            bitmap = mImageResizer.decodeSampledBitmapFromFileDesciption(fileDescriptor
                    , width, height);
            if (bitmap != null) {
                addBitmapToMemoryCache(key, bitmap);
            }
        }
        return bitmap;
    }

    private boolean downLoadUrlToStream(String url, OutputStream outputStream) {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(OkHttpUtil
                    .getImageData(url), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int bit;
            while ((bit = in.read()) != -1) {
                out.write(bit);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "download failed." + e, e);
        } finally {
            IOUtils.close(out);
            IOUtils.close(in);
        }
        return false;
    }

    private Bitmap downloadBitmapFromUrl(String url) {
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(OkHttpUtil
                    .getImageData(url), IO_BUFFER_SIZE);

            return BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "download failed." + e, e);
        } finally {
            IOUtils.close(in);
        }
        return null;
    }


    private Bitmap getBitmapFromMemory(String key) {
        return mLruCache.get(key);
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        Log.v(TAG, "addBitmapToMemoryCache" + key);
        mLruCache.put(key, bitmap);
    }

    /**
     * Md5加密
     */
    private String hashKeyFromUrl(String url) {
        String cacheKey;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(url.getBytes());
            cacheKey = bytesToHexString(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    /**
     * 二进制转换为16进制
     */
    private String bytesToHexString(byte[] digest) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            String hex = Integer.toHexString(0xFF & digest[i]);
            if (hex.length() == 1) {
                builder.append(0);
            }
            builder.append(hex);
        }
        return builder.toString();
    }
}
