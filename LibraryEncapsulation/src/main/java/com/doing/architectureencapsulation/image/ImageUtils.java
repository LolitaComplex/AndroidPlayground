package com.doing.architectureencapsulation.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-13.
 */

public class ImageUtils {

    private static MemoryCache sMemoryCache = null;
    private static GlideCircleTransform sGlideCircleTransform = null;
    private static SparseArray<GlideRoundTransform> sRoundTransforms = new SparseArray<>();

    /*package*/
    static MemoryCache getMemroyCache(Context context) {
        if (sMemoryCache == null) {
            synchronized (ImageUtils.class) {
                if (sMemoryCache == null) {
                    MemorySizeCalculator calculator = new MemorySizeCalculator(context);
                    sMemoryCache = new LruResourceCache(calculator.getMemoryCacheSize());
                }
            }
        }
        return sMemoryCache;
    }

    private static BitmapTransformation getCircleBitmapTransform(Context context) {
        if (sGlideCircleTransform == null) {
            synchronized (ImageUtils.class) {
                if (sGlideCircleTransform == null) {
                    sGlideCircleTransform = new GlideCircleTransform(context);
                }
            }
        }
        return sGlideCircleTransform;
    }

    private static BitmapTransformation getRoundBitmapTransform(Context context, int raduisDp) {
        GlideRoundTransform transformation = sRoundTransforms.get(raduisDp);

        if (transformation == null) {
            synchronized (ImageUtils.class) {
                transformation = sRoundTransforms.get(raduisDp);
                if (transformation == null) {
                    transformation = new GlideRoundTransform(context, raduisDp);
                    sRoundTransforms.put(raduisDp, transformation);
                }
            }
        }
        return transformation;
    }

    //针对网络图片的接口
    public static void setUrl(ImageView imageView, String url) {
        setUrl(imageView, url, 0, 0, null);
    }

    public static void setUrl(ImageView imageView, String url, int width, int height) {
        setUrl(imageView, url, width, height, null);
    }

    public static void setUrl(ImageView imageView, String url, int radiusDp) {
        BitmapTransformation transformation = getRoundBitmapTransform(imageView.getContext(), radiusDp);
        setUrl(imageView, url, 0, 0, transformation);
    }

    public static void setUrl(ImageView imageView, String url, int width, int height, int radiusDp) {
        BitmapTransformation transformation = getRoundBitmapTransform(imageView.getContext(), radiusDp);
        setUrl(imageView, url, width, height, transformation);
    }

    public static void setCircleUrl(ImageView imageView, String url) {
        BitmapTransformation transformation = getCircleBitmapTransform(imageView.getContext());
        setUrl(imageView, url, 0, 0, transformation);
    }

    public static void setCircleUrl(ImageView imageView, String url, int width, int height) {
        BitmapTransformation transformation = getCircleBitmapTransform(imageView.getContext());
        setUrl(imageView, url, width, height, transformation);
    }

    private static void setUrl(ImageView imageView, String url, int width, int height, BitmapTransformation transformation) {
        String sizeUrl = (width > 0 && height > 0) ?
                UrlGenerator.getImageUrl(url, width, height) : url;

        DrawableRequestBuilder<String> builder = Glide.with(imageView.getContext())
                .load(sizeUrl)
                .centerCrop()
                .animate(android.R.anim.fade_in);

        if (transformation != null) {
            builder.bitmapTransform(transformation);
        }


        builder.into(imageView);
    }

    //因为使用的是Glide，在显示本地图片时会对图片进行自动缩放，所以不用我们缩放咯
    //针对本地图片的接口
    public static void setResId(ImageView imageView, int resId) {
        setResId(imageView, resId, null);
    }

    public static void setResId(ImageView imageView, int resId, int radiusDp) {
        setResId(imageView, resId, getRoundBitmapTransform(imageView.getContext(), radiusDp));
    }

    public static void setCircleResId(ImageView imageView, int resId) {
        setResId(imageView, resId, getCircleBitmapTransform(imageView.getContext()));
    }

    private static void setResId(ImageView imageView, int resId, BitmapTransformation transformation) {
        DrawableRequestBuilder<Integer> builder = Glide.with(imageView.getContext())
                .load(resId)
                .centerCrop()
                .animate(android.R.anim.fade_in);

        if (transformation != null) {
            builder.transform(transformation);
        }

        builder.into(imageView);
    }

    //针对本地SD的接口
    public static void setFilePath(ImageView imageView, String filePath) {
        setFilePath(imageView, filePath, null);
    }

    public static void setFilePath(ImageView imageView, String filePath, int radius) {
        setFilePath(imageView, filePath, getRoundBitmapTransform(imageView.getContext(), radius));
    }

    public static void setCircleFilePath(ImageView imageView, String filePath) {
        setFilePath(imageView, filePath, getCircleBitmapTransform(imageView.getContext()));
    }

    private static void setFilePath(ImageView imageView, String filepath, BitmapTransformation transformation) {
        DrawableRequestBuilder<String> builder = Glide.with(imageView.getContext())
                .load(filepath)
                .centerCrop()
                .animate(android.R.anim.fade_in);

        if (transformation != null) {
            builder.transform(transformation);
        }

        builder.into(imageView);
    }

    //其他
    public static long getDiskCacheSize(Context context) {
        File diskCacheDir = getDiskCacheDir(context);
        return getFolderSize(diskCacheDir);
    }

    //图片选择器很容易就会选择使用第三方的库，可是这个库的缓存肯定与我们的缓存是两套，如果我们进入选择器之前不清除
    //我们的缓存，那么很可能就会造成OOM
    public static void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    public static void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    //分享操作，如果我们分享一张图片，最好的做法是事先把图片下载下来存到内存缓存中，等到分享跳转时直接从内存中读取，而不是网络加载。
    public static void prefetch(Context context, String url, int width, int height) {
        String sizeUrl = (width > 0 && height > 0) ?
                UrlGenerator.getImageUrl(url, width, height) : url;

        Glide.with(context).load(sizeUrl).downloadOnly(width, height);
    }

    public static void prefetch(Context context, String url, int width, int height, ImageLoaderListener listener) {
        String sizeUrl = (width > 0 && height > 0) ?
                UrlGenerator.getImageUrl(url, width, height) : url;

    }

    private static long getFolderSize(File file) {
        long size = 0;
        try {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File subFile : files) {
                    if (subFile.isDirectory()) {
                        size += getFolderSize(subFile);
                    } else {
                        size += subFile.length();
                    }
                }
            } else {
                size += file.length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 返回Glide本地磁盘缓存路径
     * @param context
     * @return
     */
    private static File getDiskCacheDir(Context context) {
        File cacheDirectory = context.getCacheDir();
        return new File(cacheDirectory, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR);
    }


    private static class DownloadTarget implements Target<File> {

        @Override
        public void onLoadStarted(Drawable placeholder) {

        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {

        }

        @Override
        public void onLoadCleared(Drawable placeholder) {

        }

        @Override
        public void getSize(SizeReadyCallback cb) {

        }

        @Override
        public void setRequest(Request request) {

        }

        @Override
        public Request getRequest() {
            return null;
        }

        @Override
        public void onStart() {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onDestroy() {

        }
    }
}
