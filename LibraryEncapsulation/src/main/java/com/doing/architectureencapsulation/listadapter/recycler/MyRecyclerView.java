package com.doing.architectureencapsulation.listadapter.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-30.
 */

public class MyRecyclerView extends RecyclerView {

    private static final String TAG = "MyRecyclerView";

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean flag = ViewCompat.canScrollVertically(this, -1);
        Log.d(TAG, "onInterceptTouchEvent: " + flag);
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean flag = ViewCompat.canScrollVertically(this, -1);
        Log.d(TAG, "dispatchTouchEvent: " + flag);
        return super.dispatchTouchEvent(ev);
    }
}
