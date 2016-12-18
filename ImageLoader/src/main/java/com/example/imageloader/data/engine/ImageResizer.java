package com.example.imageloader.data.engine;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

public class ImageResizer {

    private static final String TAG = "ImageResizer";

    public ImageResizer() {
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int id,
                                                  int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, id, options);
        int sampleSize = calculateSmapleSize(options, width, height);
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, id);
    }

    public Bitmap decodeSampledBitmapFromFileDesciption(FileDescriptor fd,
                                                        int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        int sampleSize = calculateSmapleSize(options, width, height);
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    private int calculateSmapleSize(BitmapFactory.Options options, int width, int height) {
        if (width == 0 || height == 0) {
            return 1;
        }

        final int imageWidth = options.outWidth;
        final int imageHeight = options.outHeight;

        int simpleSize = 1;

        if (imageWidth > width || imageHeight > height) {
            int halfImageWidth = imageWidth / 2;
            int halfImageHeight = imageHeight / 2;
            while (halfImageHeight / simpleSize > height ||
                    halfImageWidth / simpleSize > width) {
                simpleSize *= 2;
            }
        }

        return simpleSize;
    }
}
