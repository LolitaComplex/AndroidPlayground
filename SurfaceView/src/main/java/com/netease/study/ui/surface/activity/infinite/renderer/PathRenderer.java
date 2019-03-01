package com.netease.study.ui.surface.activity.infinite.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.netease.study.ui.surface.activity.infinite.PathFrame;
import com.netease.study.ui.surface.base.Frame;
import com.netease.study.ui.surface.base.FrameRenderer;

public class PathRenderer implements FrameRenderer {
    @Override
    public void render(Canvas canvas, Frame frame) {
        if (frame instanceof PathFrame) {
            render(canvas, ((PathFrame) frame).getX(), ((PathFrame) frame).getY());
        }
    }

    private static final Paint paint = new Paint();

    static {
        paint.setColor(Color.BLUE);
    }

    private void render(Canvas canvas, float x, float y) {
//        canvas.drawColor(Color.BLACK);

        canvas.drawCircle(x, y, 50, paint);
    }
}
