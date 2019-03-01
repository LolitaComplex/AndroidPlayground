package com.netease.study.ui.surface.activity.finite.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.netease.study.ui.surface.finite.FiniteFrameRenderer;

public class AlphaRenderer extends FiniteFrameRenderer {
    public AlphaRenderer() {
    }

    @Override
    public int getCount() {
        return 1;
    }

    private static final Paint paintClear = new Paint();

    private static final Paint paintAlpha = new Paint();

    static {
        paintClear.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        paintAlpha.setColor(Color.RED);
        paintAlpha.setAlpha(128);
    }

    @Override
    public void render(Canvas canvas, int number) {
        canvas.drawPaint(paintClear);
        canvas.drawPaint(paintAlpha);
    }
}
