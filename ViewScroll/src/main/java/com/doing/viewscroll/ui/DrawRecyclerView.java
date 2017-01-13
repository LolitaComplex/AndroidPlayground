package com.doing.viewscroll.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DrawRecyclerView extends LinearLayout {

    private static final String TAG = "DrawRecyclerView";
    private ViewDragHelper mViewDragHelper;

    private int mCaptureViewPosition;
    private float mCaptureViewX;
    private float mCaptureViewY;
    private List<Animator> mAnimatorList;
    private View mCapturedChild;
    private List<View> mAllChild = new ArrayList<>();
    private DragCallback dragCallback;

    public DrawRecyclerView(Context context) {
        super(context);
    }

    public DrawRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
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
            return left;
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            DrawRecyclerView parent = DrawRecyclerView.this;
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
            ViewCompat.postInvalidateOnAnimation(DrawRecyclerView.this);
        }
    }

    private boolean offsetHalfHeight(View child, int top) {
        int height = child.getHeight();
        int absTop = (Math.abs(top));
        Log.d(TAG, "Top=" + top + "\tCaptureViewY=" + mCaptureViewY + "\theight" + height);
        return (Math.abs(absTop - mCaptureViewY) % height) >= height / 2;
    }

    private void verticalChildAnimate(View captureView, int exchange) {
        View nextChild = mAllChild.get(mCaptureViewPosition + exchange);
        Log.d(TAG, "NextChild：x=" + nextChild.getX() + "\ty=" + nextChild.getY());
        Log.d(TAG, "CaptureView：x=" + mCaptureViewX + "\ty=" + mCaptureViewY);


        addAnimatorInList(nextChild, mCaptureViewX, mCaptureViewY);
        startAllAnimator();
        Collections.swap(mAllChild, mCaptureViewPosition, mCaptureViewPosition + exchange);
        mCaptureViewPosition += exchange;
    }

    private void otherChildAnimate(View captureView) {
        ViewGroup parent = (ViewGroup) captureView.getParent();

        for (int x = mCaptureViewPosition, y = mCaptureViewPosition;
             x >= 0 && y <= parent.getChildCount();) {

            if (x > 0) {
                x--;
            } else {
                continue;
            }

            if (y < parent.getChildCount()) {
                y++;
            } else {
                continue;
            }

            if (x == mCaptureViewPosition - 1 || y == mCaptureViewPosition + 1) {
                addAnimatorInList(parent.getChildAt(x), mCaptureViewX, mCaptureViewY);
                addAnimatorInList(parent.getChildAt(y), mCaptureViewX, mCaptureViewY);
            } else {
                addAnimatorInList(parent.getChildAt(x), parent.getChildAt(x + 1));
                addAnimatorInList(parent.getChildAt(y), parent.getChildAt(y - 1));
            }
        }

        startAllAnimator();
    }

    private void startAllAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(mAnimatorList);
        animatorSet.start();
    }

    private void addAnimatorInList(View currentView, View targetView) {
        addAnimatorInList(currentView, targetView.getX(), targetView.getY());
    }

    private void addAnimatorInList(View currentView, float targetViewX, float targetViewY) {
        mAnimatorList = new ArrayList<>();

//        ObjectAnimator translationX = ObjectAnimator.ofFloat(currentView,
//                "translationX", targetViewX - currentView.getX() );
//        if (currentView.getTranslationY() == 0) {
//            translationYArr = new float[]{};
//        } else {
//            translationYArr = new float[]{currentView.getTranslationY() ,0f};
//        }
        ViewWrapwer viewWrapwer = new ViewWrapwer(currentView);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(viewWrapwer,
                "verticalOffset",0f,targetViewY - currentView.getY());

//        currentView.offsetTopAndBottom((int) (targetViewY - currentView.getY()));

//        mAnimatorList.add(translationX);

        mAnimatorList.add(translationY);
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
