package com.doing.newfeature;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-05-15.
 */

public class MyView extends View {

    private static final String TAG = "MyView";

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        String modeStr;
        if (widthMode == MeasureSpec.AT_MOST) {
            modeStr = "AT_MOST";
        } else {
            modeStr = "EXACTLY";
        }
        Log.d(TAG, "WidthMode: " + modeStr + " : " + widthSize);

        if (heightMode == MeasureSpec.AT_MOST) {
            modeStr = "AT_MOST";
        } else {
            modeStr = "EXACTLY";
        }

        Log.d(TAG, "HeightMode: " + modeStr + " : " + heightSize);

        int heightDefaultSize = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        Log.d(TAG, "onMeasure: " + heightDefaultSize);

        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                heightDefaultSize);
    }
}
