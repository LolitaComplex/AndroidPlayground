package com.netease.study.ui.surface.finite;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.netease.study.ui.surface.base.Frame;
import com.netease.study.ui.surface.base.FrameRenderer;

public abstract class FiniteFrameRenderer implements FrameRenderer {
    @Override
    public final void render(Canvas canvas, Frame frame) {
        if (frame instanceof FiniteFrame) {
            render(canvas, ((FiniteFrame) frame).getNumber());
        }
    }

    public abstract int getCount();

    public abstract void render(Canvas canvas, int number);

    private static final Rect rectText = new Rect();

    protected void renderFrameNumber(Canvas canvas, int number, Rect rect, Paint paint) {
        String text = "number " + number;

        // text bound base on point (0, 0)
        paint.getTextBounds(text, 0, text.length(), rectText);

        // frame center point
        int cx, cy;
        if (rect == null) {
            cx = canvas.getWidth() / 2;
            cy = canvas.getHeight() / 2;
        } else {
            cx = rect.centerX();
            cy = rect.centerY();
        }

        // offset between frame center and text center, that is text draw point
        int px = cx - rectText.centerX();
        int py = cy - rectText.centerY();

        canvas.drawText(text, px, py, paint);
    }
}
