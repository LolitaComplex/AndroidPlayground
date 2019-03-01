package com.netease.study.ui.surface.activity.finite.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.netease.study.ui.surface.finite.FiniteFrameRenderer;

public class ColorsRenderer extends FiniteFrameRenderer {
    private final String[] colors;

    private final int repeat;

    public ColorsRenderer(String[] colors, int repeat) {
        this.colors = colors;
        this.repeat = repeat;
    }

    private int getFrameColor(int number) {
        return Color.parseColor(colors[number % colors.length]);
    }

    @Override
    public int getCount() {
        return colors.length * repeat;
    }

    @Override
    public void render(Canvas canvas, int number) {
        int color = getFrameColor(number);

        canvas.drawColor(color);

        renderFrameNumber(canvas, number, null, paintFrameNumber);
    }

    private static final Paint paintFrameNumber = new Paint();

    static {
        setupFrameNumberPaint(paintFrameNumber);
    }

    private static final void setupFrameNumberPaint(Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        paint.setStrokeWidth(5);
    }
}
