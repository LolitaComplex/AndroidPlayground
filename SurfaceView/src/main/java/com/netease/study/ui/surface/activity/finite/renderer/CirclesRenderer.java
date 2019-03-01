package com.netease.study.ui.surface.activity.finite.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.netease.study.ui.surface.finite.FiniteFrameRenderer;

public class CirclesRenderer extends FiniteFrameRenderer {
    private final int degree;

    private final int count;

    public CirclesRenderer(int degree, int count) {
        this.degree = degree;
        this.count = count;
    }

    private double getFrameDegree(int number) {
        return 2 * Math.PI / 360 * degree * number;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void render(Canvas canvas, int number) {
        // frame center point
        float cx = canvas.getWidth() / 2;
        float cy = canvas.getHeight() / 2;
        // circle radius 1 / 4 screen
        float radius = Math.min(canvas.getWidth(), canvas.getHeight()) / 4;
        // frame degree
        double degree = getFrameDegree(number);
        float px = (float) (cx + radius * Math.cos(degree));
        float py = (float) (cy + radius * Math.sin(degree));

        canvas.drawColor(Color.BLACK);

        canvas.drawLine(0, cy, canvas.getWidth(), cy, paint);
        canvas.drawLine(cx, 0, cx, canvas.getHeight(), paint);
        canvas.drawCircle(cx, cy, radius, paint);
        canvas.drawLine(cx, cy, px, py, paint);
        canvas.drawCircle(px, py, radius / 2, paint);
    }

    private static final Paint paint = new Paint();

    static {
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
    }
}
