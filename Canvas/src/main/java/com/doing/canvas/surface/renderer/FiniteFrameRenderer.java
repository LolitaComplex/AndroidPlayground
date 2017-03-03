package com.doing.canvas.surface.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.doing.canvas.surface.base.Frame;
import com.doing.canvas.surface.base.Renderer;
import com.doing.canvas.surface.frame.FiniteFrame;


/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-02-07.
 */

public abstract class FiniteFrameRenderer implements Renderer {

    private static Paint mPaint = new Paint();

    static {
        initializePaint(mPaint);
    }

    private static void initializePaint(Paint paint) {
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setTextSize(100);
    }

    @Override
    public void render(Canvas canvas, Frame frame) {
        render(canvas, ((FiniteFrame) frame).getNumber());
    }

    public abstract void render(Canvas canvas, int number);

    public abstract int getCount();

    private static final Rect mRectText = new Rect();


    protected void renderFrameNumber(Canvas canvas, int number, Rect rect) {
        String text = "Number" + number;

        mPaint.getTextBounds(text, 0, text.length(), mRectText);

        //获取画布或者指定区域中心点
        int cx, cy;
        if (rect == null) {
            cx = canvas.getWidth() / 2;
            cy = canvas.getHeight() / 2;
        } else {
            cx = rect.centerX();
            cy = rect.centerY();
        }

        //中心点水平垂直方向分别移动文字宽高的一半，这就是文字的起画的
        int px = cx - mRectText.centerX();
        int py = cy - mRectText.centerY();

        canvas.drawText(text, px, py, mPaint);
    }

}
