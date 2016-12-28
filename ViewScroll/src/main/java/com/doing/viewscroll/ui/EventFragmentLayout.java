package com.doing.viewscroll.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class EventFragmentLayout extends FrameLayout {


    public EventFragmentLayout(Context context) {
        super(context);
    }

    public EventFragmentLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EventFragmentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("EventFragmentLayout", "MotionEvent =" + event.getAction());
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("EventFragmentLayout", "MotionEvent =" + event.getAction());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("EventFragmentLayout", "MotionEvent =" + event.getAction());
                break;
            case MotionEvent.ACTION_UP:
                Log.e("EventFragmentLayout", "MotionEvent =" + event.getAction());
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e("EventFragmentLayout", "MotionEvent =" + event.getAction());
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
