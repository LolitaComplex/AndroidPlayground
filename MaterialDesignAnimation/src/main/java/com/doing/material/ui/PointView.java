package com.doing.material.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-08.
 */

public class PointView extends LinearLayout {

    private static final String TAG = "PointView";


    private Paint mPaint;
    private PointF[] mPoints;


    public PointView(Context context) {
        this(context, null);
    }

    public PointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onFinishInflate() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);

        mPoints = new PointF[]{
                new PointF(200, 200),new PointF(300, 200),new PointF(400, 600)};
        Log.d(TAG, "onFinishInflate: ");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d(TAG, "draw: ");
        canvas.drawCircle(mPoints[0].x, mPoints[0].y, 10, mPaint);
        canvas.drawCircle(mPoints[1].x, mPoints[1].y, 10, mPaint);
        canvas.drawCircle(mPoints[2].x, mPoints[2].y, 10, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");
    }
}
