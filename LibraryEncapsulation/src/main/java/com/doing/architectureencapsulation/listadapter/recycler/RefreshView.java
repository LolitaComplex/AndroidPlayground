package com.doing.architectureencapsulation.listadapter.recycler;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.doing.architectureencapsulation.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-31.
 */

public class RefreshView extends ViewGroup implements NestedScrollingParent,NestedScrollingChild{

    private static final String TAG = "RefreshView";

    private Context mContext;
    private View mRefreshView;
    private int mTouchSlop;
    private View mTarget;
//    private View mRefreshViewParent;
    private boolean isRefresh;

    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
    private float mTotalUnconsumed;
    private boolean mNestedScrollInProgress;

    private final int[] mParentScrollConsumed = new int[2];
    private final int[] mParentOffsetInWindow = new int[2];

    static final int CIRCLE_DIAMETER = 68;
    private int mCircleDiameter;

    private ScrollerCompat mScroller;
    private OnRefreshListener mOnRefreshListener;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATE_DEFAULT, STATE_PREPARE, STATE_CAN_REFRESH, STATE_REFRESHING})
    public @interface StateMode {
    }

    private static final int STATE_DEFAULT = 0;
    private static final int STATE_PREPARE = 1;
    private static final int STATE_CAN_REFRESH = 3;
    private static final int STATE_REFRESHING = 4;

    @StateMode
    private int mCurrentState = STATE_DEFAULT;

    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = getContext();
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);

        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
        ViewCompat.setNestedScrollingEnabled(this, true);

        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        mCircleDiameter = (int) (CIRCLE_DIAMETER * metrics.density);

        mScroller = ScrollerCompat.create(getContext());
    }

    @Override
    protected void onFinishInflate() {
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        createRefreshView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }
        mTarget.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth()
                - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY), heightMeasureSpec);
//        mRefreshView.measure(widthMeasureSpec, heightMeasureSpec);
        measureChildWithMargins(mRefreshView, widthMeasureSpec, 0, heightMeasureSpec, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        int refreshHeight = mRefreshView.getMeasuredHeight();
        int refreshWidth = mRefreshView.getMeasuredWidth();
        mRefreshView.layout((width - refreshWidth)/2, childTop, (width + refreshWidth)/2, refreshHeight);
        mTarget.layout(childLeft, childTop + refreshHeight, childWidth, childHeight + refreshHeight);
        scrollTo(0, refreshHeight);
    }

    public void setRefreshView(View view) {
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new MarginLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, mCircleDiameter)));
        imageView.setImageResource(R.mipmap.ic_launcher);
        mRefreshView = imageView;
    }

    public void setRefreshText(String[] text) {

    }

    public void setRefreshAnim(Animator anim) {

    }

    private void createRefreshView() {
        setRefreshView(null);
//        FrameLayout refreshParent = new FrameLayout(mContext);
//        refreshParent.setClipChildren(false);
//        refreshParent.setPadding(0, 10, 0, 10);
//        refreshParent.setLayoutParams(new MarginLayoutParams(new LayoutParams(
//                LayoutParams.MATCH_PARENT, mCircleDiameter)));
//        refreshParent.addView(mRefreshView);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.CENTER;
//        mRefreshView.setLayoutParams(layoutParams);
        this.addView(mRefreshView);
    }

    private void ensureTarget() {
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!child.equals(mRefreshView)) {
                    mTarget = child;
                    break;
                }
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();

        Log.w(TAG, "onInterceptTouchEvent: ");
        if (!isEnabled() || canChildScrollUp() || isRefresh || mNestedScrollInProgress) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean canChildScrollUp() {
        return ViewCompat.canScrollVertically(mTarget, -1);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes){
        return isEnabled() && !isRefresh && (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes){
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);

        ViewCompat.startNestedScroll(this, nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL);
        mTotalUnconsumed = 0;
        mNestedScrollInProgress = true;
    }

    @Override
    public void onStopNestedScroll(View target){
        mNestedScrollingParentHelper.onStopNestedScroll(target);
        if (mTotalUnconsumed > 0) {
            if (mTotalUnconsumed > 2 * mCircleDiameter) {
                smoothScrollTo(0, 0);
                int previoudState = initState(-1);
                doSomething(previoudState);
            } else {
                smoothScrollTo(0, mCircleDiameter);
                initState(0);
            }
            mTotalUnconsumed = 0;
        }
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed){
        if (dy > 0 && mTotalUnconsumed > 0) {
            if (dy > mTotalUnconsumed) {
                consumed[1] = dy - (int) mTotalUnconsumed;
                mTotalUnconsumed = 0;
            } else {
                mTotalUnconsumed -= dy;
                consumed[1] = dy;
            }
            moveRefreshView(dy);
        }

        final int[] parentConsumed = mParentScrollConsumed;
        if (ViewCompat.dispatchNestedPreScroll(this, dx - consumed[0], dy - consumed[1], parentConsumed, null)) {
            consumed[0] += parentConsumed[0];
            consumed[1] += parentConsumed[1];
        }
    }

    @StateMode
    private int initState(float totalUnconsumed) {
        int previousState = mCurrentState;
        if (totalUnconsumed > 2 * mCircleDiameter) {
            mCurrentState = STATE_CAN_REFRESH;
        } else if (totalUnconsumed > 0 && totalUnconsumed <= 2 * mCircleDiameter){
            mCurrentState = STATE_PREPARE;
        } else if (totalUnconsumed == 0) {
            mCurrentState = STATE_DEFAULT;
        } else {
            mCurrentState = STATE_REFRESHING;
        }
        return previousState;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed){
        ViewCompat.dispatchNestedScroll(this, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                mParentOffsetInWindow);

        final int dy = dyUnconsumed + mParentOffsetInWindow[1];
        if (dy < 0 && !canChildScrollUp()) {
            mTotalUnconsumed += Math.abs(dy);
            moveRefreshView(dy);
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed){
        return dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY){
        return ispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public int getNestedScrollAxes(){
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    private boolean ispatchNestedPreFling(float velocityX, float velocityY) {
        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }


    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    private void moveRefreshView(float overscrollTop) {
        int previousState = initState(mTotalUnconsumed);
        scrollBy(0, (int) overscrollTop/2);
        doSomething(previousState);
    }

    private void doSomething(@StateMode int previousState) {
        if (previousState != mCurrentState) {
            switch (mCurrentState) {
                case STATE_CAN_REFRESH:
                    rotate(180);
                    break;

                case STATE_REFRESHING:
                    isRefresh = true;
                    //瞎写的，请无视
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(mRefreshView, View.SCALE_X, 1f, .5f);
                    scaleX.setRepeatCount(500);
                    scaleX.setRepeatMode(ValueAnimator.REVERSE);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(mRefreshView, View.SCALE_Y, 1f, .5f);
                    scaleY.setRepeatCount(500);
                    scaleX.setRepeatMode(ValueAnimator.REVERSE);
                    ObjectAnimator rotate = ObjectAnimator.ofFloat(mRefreshView, View.ROTATION, 0,360);
                    rotate.setDuration(2000);
                    rotate.setRepeatCount(100);
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(scaleX, scaleY, rotate);
                    set.start();
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onRrefresh();
                    }
                    initState(0);
                    break;

                case STATE_PREPARE:
                    if (previousState == STATE_CAN_REFRESH) {
                        rotate(0);
                    }
                    break;
            }
        }
    }

    public interface OnRefreshListener{
        void onRrefresh();
    }

    public void setOnRefreshListener(OnRefreshListener refreshListener){
        mOnRefreshListener = refreshListener;
    }

    private void rotate(int end) {
        ViewCompat.animate(mRefreshView)
                .rotation(end)
                .start();
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
}
