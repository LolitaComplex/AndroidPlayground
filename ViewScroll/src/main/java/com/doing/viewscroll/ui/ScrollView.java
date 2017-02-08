package com.doing.viewscroll.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.widget.ScrollerCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import static android.R.attr.translationX;
import static android.R.attr.translationY;

public class ScrollView extends ImageView {

    public static final String TAG = "ScrollView";
    private int startX;
    private int startY;

    private ScrollerCompat mScroller;

    public ScrollView(Context context) {
        super(context);
        mScroller = ScrollerCompat.create(getContext());
    }

    public ScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = ScrollerCompat.create(getContext());
    }

    private int mWidth;
    private int mHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST &&
                heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, mHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int moveX = (int) event.getRawX();
        int moveY = (int) event.getRawY();


        //当ViewParent不拦截ACTION_DOWN事件，不过拦截其他事件时，则可以通过此方法强制不让父控件拦截
//        getParent().requestDisallowInterceptTouchEvent(true);
        boolean actionDown = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaX = moveX - startX;
                int deltaY = moveY - startY;
//                Log.e(TAG, "deltaX = " + deltaX + " === deltaY = " + deltaY);
                int translationX = (int) (ViewCompat.getTranslationX(this) + deltaX);
                int translationY = (int) (ViewCompat.getTranslationY(this) + deltaY);

                //移动方式1
//                layout(getLeft() + translationX, getTop() + translationY, getRight()
//                        + translationX, getBottom() + translationY);

                //移动方式2
//                offsetLeftAndRight(translationX);
//                offsetTopAndBottom(translationY);

                //移动方式3
//                ((ViewGroup) getParent()).scrollBy(-translationX, -translationY);

                //移动方式4
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                layoutParams.topMargin = getTop() + translationY;
//                layoutParams.leftMargin = getLeft() + translationX;
//                setLayoutParams(layoutParams);

                //移动方式5
                ViewCompat.setTranslationX(this, translationX);
                ViewCompat.setTranslationY(this, translationY);

//                ViewCompat.offsetLeftAndRight(this, translationX);
//                ViewCompat.offsetTopAndBottom(this, translationY);
                actionDown = false;
                break;

            case MotionEvent.ACTION_DOWN:
                actionDown = true;
                break;
            case MotionEvent.ACTION_UP:
                actionDown = false;
                break;
            default:
                break;
        }

        ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Log.v(TAG, "getTranslationX() =" + getTranslationX() + ":::::::getTranslationY()=" + getTranslationY());
        Log.d(TAG, "getX() =" + getX() + ":::::::getY()=" + getY());
        Log.w(TAG, "left =" + getLeft() + "\ttop=" + getTop() + "\tright=" + getRight() + "\tbottom=" + getBottom());
        Log.i(TAG, "scrollX =" + getScrollX() + "\tscrollY=" + getScrollY());
        startX = moveX;
        startY = moveY;
        boolean touchEvent = super.onTouchEvent(event);

        //初始化
//        VelocityTracker tracker = VelocityTracker.obtain();
//        tracker.addMovement(event);
//        tracker.computeCurrentVelocity(1000);
//        float xVelocity = tracker.getXVelocity();
//        float yVelocity = tracker.getYVelocity();
//        //使用完毕清空资源
//        tracker.clear();
//        tracker.recycle();

//        Log.e(TAG, "xVelocity = " + tracker.getXVelocity() + "\tyVelocity" + tracker.getYVelocity());

        return actionDown && touchEvent;
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;

        int scrollY = getScrollY();
        int deltaY = destY - scrollY;
        mScroller.startScroll(scrollX, scrollY, deltaX, deltaY, 1000);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
//            int width = view.getMeasuredWidth();
//            int height = view.getMeasuredHeight();
        }
    }
}
