package com.netease.study.ui.surface.activity.finite.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.netease.study.ui.surface.finite.FiniteFrameRenderer;

public class SquaresRenderer extends FiniteFrameRenderer {
    private final int hCount;

    private final int vCount;

    private static final Paint paintFrame = new Paint();

    private static final Rect rectFrame = new Rect();

    public SquaresRenderer(int hCount, int vCount) {
        this.hCount = hCount;
        this.vCount = vCount;
    }

    private void getFrameRect(int width, int height, int number, Rect rect) {
        int w = width / hCount;
        int h = height / vCount;
        rect.left = number % hCount * w;
        rect.top = number / hCount * h;
        rect.right = rect.left + w;
        rect.bottom = rect.top + h;
    }

    private int getFrameColor(int number) {
        // from 32 - 224
        int gray = 32 + 192 * number / (hCount * vCount);
        return Color.rgb(gray, gray, gray);
    }

    @Override
    public int getCount() {
        return hCount * vCount;
    }

    @Override
    public void render(Canvas canvas, int number) {
        getFrameRect(canvas.getWidth(), canvas.getHeight(), number, rectFrame);
        int color = getFrameColor(number);

        paintFrame.setColor(color);

//        canvas.drawColor(Color.BLACK);

        canvas.drawRect(rectFrame, paintFrame);

        renderFrameNumber(canvas, number, rectFrame, paintFrameNumber);
    }

    private static final Paint paintFrameNumber = new Paint();

    static {
        setupFrameNumberPaint(paintFrameNumber);
    }

    private static final void setupFrameNumberPaint(Paint paint) {
        paint.setColor(Color.RED);
        paint.setTextSize(30);
        paint.setStrokeWidth(3);
    }
}
