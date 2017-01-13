package com.doing.viewscroll.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DragRecyclerView extends GridLayout {

    private static final String TAG = "DrawRecyclerView";
    private ViewDragHelper mViewDragHelper;

    private int mCaptureViewPosition;
    private float mCaptureViewX;
    private float mCaptureViewY;
    private List<Animator> mAnimatorList;
    private View mCapturedChild;
    private List<View> mAllChild = new ArrayList<>();
    private DragCallback dragCallback;

    public DragRecyclerView(Context context) {
        super(context);
    }

    public DragRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        dragCallback = new DragCallback();
        mViewDragHelper = ViewDragHelper.create(
                this,dragCallback);
        mAllChild.clear();
        for (int i = 0; i < this.getChildCount(); i++) {
            mAllChild.add(getChildAt(i));
        }
    }

    private class DragCallback extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
//            otherChildAnimate(child);
            if (offsetHalfHeight(child, top) ) {
                if (top > mCaptureViewY) {
                    verticalChildAnimate(child, 1);
                    mCaptureViewY += child.getHeight();
                } else {
                    verticalChildAnimate(child, -1);
                    mCaptureViewY -= child.getHeight();
                }
            }
            return top;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (offsetHalfWidth(child, left) ) {
                if (left > mCaptureViewX) {
                    horizationChildAnimate(1);
                    mCaptureViewX += child.getWidth();
                } else {
                    horizationChildAnimate(-1);
                    mCaptureViewX -= child.getWidth();
                }
            }
            return left;
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            DragRecyclerView parent = DragRecyclerView.this;
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (capturedChild == mAllChild.get(i)) {
                    mCaptureViewPosition = i;
                    mCaptureViewX = capturedChild.getX();
                    mCaptureViewY = capturedChild.getY();
                }
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            mViewDragHelper.settleCapturedViewAt((int)mCaptureViewX, (int)mCaptureViewY);
            ViewCompat.postInvalidateOnAnimation(DragRecyclerView.this);
        }
    }

    private boolean offsetHalfHeight(View child, int top) {
        int height = child.getHeight();
//        Log.d(TAG, "Top=" + top + "\tCaptureViewY=" + mCaptureViewY + "\theight" + height);
        return (Math.abs(top - mCaptureViewY) % height) >= height / 2;
    }

    private boolean offsetHalfWidth(View child, int left) {
        int width = child.getWidth();
        Log.d(TAG, "Left=" + left + "\tCaptureViewX=" + mCaptureViewY + "\twidth" + width);
        return (Math.abs(left - mCaptureViewX) % width) >= width / 2;
    }



    private void verticalChildAnimate(View captureView, int exchange) {
        View nextChild = mAllChild.get(mCaptureViewPosition + exchange * getColumnCount());
//        Log.d(TAG, "NextChild：x=" + nextChild.getX() + "\ty=" + nextChild.getY());
//        Log.d(TAG, "CaptureView：x=" + mCaptureViewX + "\ty=" + mCaptureViewY);
        TransitionManager.beginDelayedTransition((ViewGroup) nextChild);


        addVerticalAnimatorInList(nextChild, mCaptureViewY);
        startAllAnimator();
        Collections.swap(mAllChild, mCaptureViewPosition, mCaptureViewPosition + exchange * getColumnCount());
        mCaptureViewPosition += exchange * getColumnCount();
    }

    private void horizationChildAnimate(int exchange) {
        View nextChild = mAllChild.get(mCaptureViewPosition + exchange);
        TransitionManager.beginDelayedTransition((ViewGroup) nextChild);
        Log.d(TAG, "NextChild：x=" + nextChild.getX() + "\ty=" + nextChild.getY());
        Log.d(TAG, "CaptureView：x=" + mCaptureViewX + "\ty=" + mCaptureViewY);

        startAllAnimator();
        Collections.swap(mAllChild, mCaptureViewPosition, mCaptureViewPosition + exchange);
        mCaptureViewPosition += exchange;

    }


    private void startAllAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(mAnimatorList);
        animatorSet.start();
    }

    private void addVerticalAnimatorInList(final View currentView, float targetViewY) {
        mAnimatorList = new ArrayList<>();

        //ViewDrag判断点击的View与移动都是通过offsetLeft等实现的，所以这种属性动画不能使用
//        float[] translationYArr;
//        if (currentView.getTranslationY() == 0) {
//            translationYArr = new float[]{targetViewY - currentView.getY()};
//        } else {
//            translationYArr = new float[]{currentView.getTranslationY() ,0f};
//        }
//
//        ObjectAnimator translationX = ObjectAnimator.ofFloat(currentView,
//                "translationX", targetViewX - currentView.getX() );
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(currentView,
//                "translationY", translationYArr);

          //第二种看起来可以，不过由于硬件加速导致属性动画offset值有问题，问题不明
//        ViewWrapwer viewWrapwer = new ViewWrapwer(currentView);
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(viewWrapwer,
//                "verticalOffset",0f,targetViewY - currentView.getY());

//        currentView.offsetTopAndBottom((int) (targetViewY - currentView.getY()));

//        mAnimatorList.add(translationX);

//        mAnimatorList.add(translationY);
        //最后一种迫不得已使用TransitionManager来实现
        currentView.offsetTopAndBottom((int) (targetViewY - currentView.getY()));

    }

    private void addHorzitalAnimatorInList(final View currentView, float targetViewX) {
        mAnimatorList = new ArrayList<>();
        currentView.offsetLeftAndRight((int) (targetViewX - currentView.getX()));
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class ViewWrapwer {

        private View view;

        public ViewWrapwer(android.view.View view){
            this.view = view;
        }

        public void setVerticalOffset(float offset) {
            int offsetLayout = (int) offset;
            view.offsetTopAndBottom(offsetLayout);
            Log.d(TAG, "setVerticalOffset: " + offset);
        }

        public float getVerticalOffset() {
            return 0;
        }
    }


}
