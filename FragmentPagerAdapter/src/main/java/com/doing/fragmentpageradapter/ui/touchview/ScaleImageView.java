package com.doing.fragmentpageradapter.ui.touchview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewTreeObserver;
import android.widget.ImageView;


/**
 * 可发大缩小的自定义View
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-05-26.
 */

public class ScaleImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final String TAG = "ScaleImageView";

    public static final float MAX_SCALE_SIZE = 1.5f;
    //手势识别
    private ScaleGestureDetector mScaleGestureDetector;
    //第一次初始化的标记
    private boolean once = true;
    private Matrix mScaleMatrix;

    private final float[] mMatrixValues = new float[9];

    private float mMinScaleSize;
    private AnimatorData mAnimatorData;
    private RectF mImageRectF;
    private GestureDetector mGestureDetector;
    private int mWidth;
    private int mHeight;
    private boolean mScaleBegin;
    private float mStartX;
    private boolean mBoundaryStart;
    private boolean mBoundaryEnd;

    public ScaleImageView(Context context) {
        super(context);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(
                getContext(), new OnScaleGestureListener());
        mScaleMatrix = new Matrix();
        mGestureDetector = new GestureDetector(this.getContext(),
                new OnTranslationGestureListener());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    private void initLayout() {
        if (once) {
            Drawable drawable = getDrawable();
            if (drawable == null)
                return;

            mWidth = getWidth();
            mHeight = getHeight();

            //拿到的是Bitmap在对应Dpi缩放后的大小，也就是实际的宽高.Bitmap.getWidth是图片的原宽
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();

            float scaleWidth = mWidth * 1.f / intrinsicWidth;
            float scaleHeight = mHeight * 1.f / intrinsicHeight;

            //CenterInside的效果
            float scale = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
            scale = Math.min(scale, 1);
            mMinScaleSize = scale;
            mScaleMatrix.setTranslate((mWidth - intrinsicWidth) / 2.f, (mHeight - intrinsicHeight) / 2.f);
            mScaleMatrix.postScale(scale, scale, mWidth / 2.f, mHeight / 2.f);
            setImageMatrix(mScaleMatrix);
            //初始化Rect
            getImageRectF();
            once = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean scaleEvent = mScaleGestureDetector.onTouchEvent(event);
        boolean clickEvent = mGestureDetector.onTouchEvent(event);

        float deltaX = event.getX();

        float scaleSize = getScaleSize();

        Log.d(TAG, "StartX: " + mStartX + "\t deltaX: " + deltaX + "\t"
                + isBoundaryStart() + "\t " + isBoundaryEnd() + "\t Action: " + event.getAction());
        if (scaleSize == mMinScaleSize) {
            getParent().requestDisallowInterceptTouchEvent(false);
            mBoundaryStart = false;
        } else {
            if (mBoundaryStart || mBoundaryEnd) {
                if (mStartX != 0 && mStartX < deltaX && isBoundaryStart() && mBoundaryStart){
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else if (mStartX != 0 && mStartX > deltaX && isBoundaryEnd() && mBoundaryEnd){
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

            } else {
                getParent().requestDisallowInterceptTouchEvent(true);
                mBoundaryStart = mBoundaryEnd = false;
            }
            changeBoundaryFlag(event);
        }

        mStartX = event.getX();
        return scaleEvent || clickEvent;
    }

    private void changeBoundaryFlag(MotionEvent event) {
        if (isBoundaryStart() && event.getAction() == MotionEvent.ACTION_UP) {
            mBoundaryStart = true;
            mBoundaryEnd = false;
        } else if (isBoundaryEnd() && event.getAction() == MotionEvent.ACTION_UP) {
            mBoundaryEnd = true;
            mBoundaryStart = false;
        } else if (event.getAction() == MotionEvent.ACTION_UP){
            mBoundaryStart = mBoundaryEnd = false;
        }
    }

    private class OnTranslationGestureListener extends GestureDetector.SimpleOnGestureListener {

        private boolean doubleFlag = false;

        //未复写onDown因为上边用的或的关系，这里返回值变得不是那么重要
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float doubleX = e.getX();
            float doubleY = e.getY();

            if (!doubleFlag) {
                mAnimatorData = new AnimatorData(doubleX, doubleY,
                        getScaleSize() <= 0.75f ? 0.75f : 1.f);
            } else {
                mAnimatorData = new AnimatorData(mWidth / 2, mHeight / 2,
                        mMinScaleSize);
            }
            doubleFlag = !doubleFlag;

            ObjectAnimator.ofFloat(ScaleImageView.this, "scaleSize",0, 1.f)
                    .setDuration(500)
                    .start();
            Log.i(TAG, "onDoubleTap: doubleX : " + doubleX + "\t doubleY : " + doubleY);
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (mScaleBegin) {
                return false;
            }
            Log.w(TAG, "onScroll: distanceX : " + distanceX + "\t distanceY : " + distanceY);
            RectF rect = getImageRectF();

            float deltaX = 0;
            float deltaY = 0;
            if (rect.width() > mWidth) {

                if (distanceX > 0 && Math.abs(rect.right - mWidth) < distanceX) {
                    deltaX = mWidth - rect.right;
                } else if (distanceX < 0 && Math.abs(rect.left) < -distanceX) {
                    deltaX = -rect.left;
                } else {
                    deltaX = -distanceX;
                }
            }

            if (rect.height() > mHeight) {
                if (distanceY > 0 && Math.abs(rect.bottom - mHeight) < distanceY) {
                    deltaY = mHeight - rect.bottom;
                } else if (distanceY < 0 && Math.abs(rect.top) < -distanceY) {
                    deltaY = -rect.top;
                } else {
                    deltaY = -distanceY;
                }
            }
            mScaleMatrix.postTranslate(deltaX, deltaY);
            setImageMatrix(mScaleMatrix);
            return true;
        }

    }

    private void scaleToTargetSize(float scaleSize, float focusX, float focusY) {
        mScaleMatrix.postScale(scaleSize, scaleSize, focusX, focusY);
        correctRect();
        setImageMatrix(mScaleMatrix);

    }

    private class OnScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.d(TAG, "onScale: FocusX" + detector.getFocusX() + "\t FocusY:" + detector.getFocusY());
            float scale = getScaleSize();
            float scaleFactor = detector.getScaleFactor();
            if (scale < MAX_SCALE_SIZE || scaleFactor < MAX_SCALE_SIZE) {
                if (scale * scaleFactor > MAX_SCALE_SIZE) {
                    scaleFactor = MAX_SCALE_SIZE / scale;
                }
//            Log.i(TAG, "onScale: scale : " + scale + "\t scaleFactor : " + scale * scaleFactor);

//            RectF rect = getImageRectF();

//            float scaleX = rect.right - rect.left > getWidth() ? detector.getFocusX() : getWidth() / 2;
//            float scaleY = rect.bottom - rect.top > getHeight() ? detector.getFocusY() : getHeight() / 2;

//            Log.i(TAG, "scaleX: " + scaleX + "\t scaleY：" + scaleY);
//            Log.d(TAG, "onScale: " + detector.getCurrentSpanX() + "\t : " + detector.getPreviousSpanX());
//            if (detector.getCurrentSpan() < detector.getPreviousSpan()) {
//            }
                scaleToTargetSize(scaleFactor, detector.getFocusX(), detector.getFocusY());
            }
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            float scale = getScaleSize();
            if (scale < mMinScaleSize) {
                Log.w(TAG, "onScaleEnd: ");
                //属性动画传递的是当前进度，其实应该再估值器中计算正确的值
                mAnimatorData = new AnimatorData(mWidth / 2, mHeight / 2,
                        mMinScaleSize);
                ObjectAnimator.ofFloat(ScaleImageView.this, "scaleSize", 1.f)
                        .setDuration(500)
                        .start();
            }
            mScaleBegin = false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleBegin: ");
            return mScaleBegin = true;
        }
    }

    private void correctRect() {
        RectF rect = getImageRectF();
//        float transitionX = 0;
//        float transitionY = 0;
//        if (rect.height() <= getHeight()) {
//            float centerY = (rect.bottom - rect.top) / 2.f;
//            float centerImageY = getHeight() / 2.f - mMatrixValues[Matrix.MTRANS_Y];
//            Log.i(TAG, "CenterY: " + centerY + "\t centerImageY：" + centerImageY);
//            transitionY = centerImageY - centerY;
//        }
//        if (rect.width() <= getWidth() + 20) {
//            float centerX = (rect.right - rect.left) / 2.f;
//            float centerImageX = getWidth() / 2.f - mMatrixValues[Matrix.MTRANS_X];
//            transitionX = centerImageX - centerX;
//        }
//        Log.i(TAG, "Transition: " + transitionX + "\t " + transitionY);
//        mScaleMatrix.postTranslate(transitionX, transitionY);
        float deltaX = 0;
        float deltaY = 0;


        Log.d(TAG, "Rect: " + rect);
        if (rect.width() > getWidth()) {
            if ((rect.left > 0)) {
                deltaX = -rect.left;
            }
            if (rect.right < mWidth) {
                deltaX = mWidth - rect.right;
            }
            Log.i(TAG, "修正X：" + deltaX);
        }

        if (rect.height() > getHeight()) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < mHeight) {
                deltaY = mHeight - rect.bottom;
            }
            Log.i(TAG, "修正Y：" + deltaY);
        }
        if (rect.width() < mWidth) {
            deltaX = mWidth / 2.f - rect.right + rect.width() / 2.f;
            Log.i(TAG, "\t偏移X: " + deltaX);
        }
        if (rect.height() < mHeight) {
            deltaY = mHeight / 2.f - rect.bottom + rect.height() / 2.f;
            Log.i(TAG, "\t偏移Y " + deltaY);
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 在Traversal之后会被回调
     */
    @Override
    public void onGlobalLayout() {
        initLayout();
    }

    public void setScaleSize(float percent) {
        //根据时间进度计算缩放大小
        if (mAnimatorData != null) {
            float scale = getScaleSize();
            float finalScale = ((mAnimatorData.targetScaleSize - scale) * percent + scale) / scale;
            scaleToTargetSize(finalScale, mAnimatorData.focusX, mAnimatorData.focusY);
            Log.i(TAG, "setScaleSize:  scale:" + scale + " finalSize:" + finalScale +
                    "\t percent:" + percent);
        }

    }

    private static class AnimatorData {

        AnimatorData(float focusX, float focusY, float targetScaleSize) {
            this.focusX = focusX;
            this.focusY = focusY;
            this.targetScaleSize = targetScaleSize;
        }

        float focusX;
        float focusY;
        float targetScaleSize;
    }


    public float getScaleSize() {
        mScaleMatrix.getValues(mMatrixValues);
        return mMatrixValues[Matrix.MSCALE_X] < mMatrixValues[Matrix.MSCALE_Y] ?
                mMatrixValues[Matrix.MSCALE_X] : mMatrixValues[Matrix.MSCALE_Y];
    }


    private boolean isBoundaryStart() {
        return getImageRectF().left == 0;
    }

    private boolean isBoundaryEnd() {
        RectF rect = getImageRectF();
        return rect.right == mWidth;
    }

    private RectF getImageRectF() {
        if (mImageRectF == null) {
            mImageRectF = new RectF();
        }
        //如果不添加Drawable宽高，Rect长宽无法得到初始化
        Drawable drawable = getDrawable();
        mImageRectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mScaleMatrix.mapRect(mImageRectF);
        return mImageRectF;
    }

}
