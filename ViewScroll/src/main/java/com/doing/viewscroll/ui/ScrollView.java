package com.doing.viewscroll.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Scroller;

public class ScrollView extends ImageView {

    public static final String TAG = "ScrollView";
    private int startX;
    private int startY;

    private Scroller mScroller;

    public ScrollView(Context context) {
        super(context);
        mScroller = new Scroller(getContext());
    }

    public ScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int moveX = (int) event.getRawX();
        int moveY = (int) event.getRawY();


        //当ViewParent不拦截ACTION_DOWN事件，不过拦截其他事件时，则可以通过此方法强制不让父控件拦截
        getParent().requestDisallowInterceptTouchEvent(true);
        boolean actionDown = true;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaX = moveX - startX;
                int deltaY = moveY - startY;
                Log.e(TAG, "deltaX = " + deltaX + " === deltaY = " + deltaY);
                int translationX = (int) (ViewCompat.getTranslationX(this) + deltaX);
                int translationY = (int) (ViewCompat.getTranslationY(this) + deltaY);
                ViewCompat.setTranslationX(this,translationX);
                ViewCompat.setTranslationY(this,translationY);
                break;

            case MotionEvent.ACTION_DOWN:
                actionDown = true;
                break;
            case MotionEvent.ACTION_UP:
                actionDown = true;
                break;
            default:
                break;
        }

        startX = moveX;
        startY = moveY;
        boolean superTouchEvnet = super.onTouchEvent(event);
        return actionDown && superTouchEvnet;
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;

        int scrollY = getScrollY();
        int deltaY = destY - scrollY;
        mScroller.startScroll(scrollX, scrollY, deltaX, deltaY, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
