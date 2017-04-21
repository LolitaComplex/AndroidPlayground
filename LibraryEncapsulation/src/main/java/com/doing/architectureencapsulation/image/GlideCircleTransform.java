package com.doing.architectureencapsulation.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-13.
 */

public class GlideCircleTransform extends BitmapTransformation {


    public GlideCircleTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if (toTransform == null) {
            return null;
        }

        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int size = Math.min(width, height);

        Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        canvas.drawCircle(size / 2, size / 2, size / 2, paint);

//        Canvas canvas = new Canvas(toTransform);
//        BitmapShader bitmapShader = new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        int min = Math.min(toTransform.getWidth(), toTransform.getHeight());
//        RadialGradient radialGradient = new RadialGradient(toTransform.getWidth() / 2, toTransform.getHeight() / 2,
//                min / 2, Color.TRANSPARENT, Color.WHITE, Shader.TileMode.CLAMP);
//        ComposeShader composeShader = new ComposeShader(bitmapShader, radialGradient, PorterDuff.Mode.CLEAR);
//        Paint paint = new Paint();
//        paint.setShader(composeShader);
//        canvas.drawRect(0, 0, toTransform.getWidth(), toTransform.getHeight(), paint);
        return result;
    }

    @Override
    public String getId() {
        return "GlideCircleTransform";
    }
}
