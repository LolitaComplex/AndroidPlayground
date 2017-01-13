package com.doing.viewscroll.ui;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


public class DragViewGroup extends FrameLayout {

    private static final String TAG = "DragViewGroup";

    private ViewDragHelper mViewDragHelper;
    private View mMenu;
    private View mContent;
    private int mMenuMeasuredWidth;

    public DragViewGroup(Context context) {
        super(context);
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        int menuWidthSpec = MeasureSpec.makeMeasureSpec(widthPixels, MeasureSpec.AT_MOST);
        int menuHeightSpec = MeasureSpec.makeMeasureSpec(heightPixels, MeasureSpec.AT_MOST);
        measureChildWithMargins(mMenu, menuWidthSpec, 0, menuHeightSpec, 0);
        mContent.measure(widthMeasureSpec, heightMeasureSpec);
        mMenuMeasuredWidth = mMenu.getMeasuredWidth();

        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mMenu.layout(-mMenuMeasuredWidth, 0, 0, bottom);
        mContent.layout(left, top, right, bottom);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenu = this.getChildAt(1);
        mContent = this.getChildAt(0);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mViewDragHelper = ViewDragHelper.create(this, mCallback);
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mMenu == child;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.d(TAG, "clampViewPositionHorizontal: " + left);
            return left >= 0 ? 0 : left;
        }

        /**
         *
         * @param child
         * @param top 垂直方向child的移动距离，当有padding等属性值时要特殊计算来得出返回值，一般返回top即可
         * @param dy 表示较前一次的增量
         * @return
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mMenu.getLeft() < mMenuMeasuredWidth / -2) {
                mViewDragHelper.smoothSlideViewTo(mMenu, -mMenuMeasuredWidth, 0);
                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
            } else {
                mViewDragHelper.smoothSlideViewTo(mMenu, 0, 0);
                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
            }
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
//            Log.d(TAG, "onEdgeTouched: " + edgeFlags + "::::::::::pointerId=" + pointerId);
            mViewDragHelper.smoothSlideViewTo(mMenu, -mMenuMeasuredWidth + 50, 0);
            ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mMenuMeasuredWidth;
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mViewDragHelper.processTouchEvent(ev);
        return true;
//        return super.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
