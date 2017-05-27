package com.doing.fragmentpageradapter.ui.touchview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import static android.R.attr.scaleX;
import static android.R.attr.scaleY;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-05-26.
 */

public class ScaleImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    public static final float MAX_SCALE_SISE = 1.5f;
    //手势识别
    private ScaleGestureDetector mScaleGestureDetector;
    //第一次初始化的标记
    private boolean once = true;
    private Matrix mScaleMatrix;

    private final float[] mMatrixValues = new float[9];

    private static final String TAG = "ScaleImageView";
    private float mMinScaleSize;
    private RectF mImageRectF;

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
                getContext(), this);
        mScaleMatrix = new Matrix();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    private void initLayout() {
        if (once) {
            Drawable drawable = getDrawable();
            if (drawable == null)
                return;

            int width = getMeasuredWidth();
            int height = getMeasuredHeight();

            //拿到的是Bitmap在对应Dpi缩放后的大小，也就是实际的宽高.Bitmap.getWidth是图片的原宽
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();

            float scaleWidth = width * 1.f / intrinsicWidth;
            float scaleHeight = height * 1.f / intrinsicHeight;

            //CenterInsdide的效果
            float scale = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
            scale = Math.min(scale, 1);
            mMinScaleSize = scale;
            mScaleMatrix.setTranslate((width - intrinsicWidth) / 2.f, (height - intrinsicHeight) / 2.f);
            mScaleMatrix.postScale(scale, scale, width / 2.f, height / 2.f);
            setImageMatrix(mScaleMatrix);
            getImageRectF();
            once = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScaleSize();
        float scaleFactor = detector.getScaleFactor();
        if (scale < MAX_SCALE_SISE || scaleFactor < MAX_SCALE_SISE) {
            if (scale * scaleFactor > MAX_SCALE_SISE) {
                scaleFactor = MAX_SCALE_SISE / scale;
            }
//            Log.i(TAG, "onScale: scale : " + scale + "\t scaleFactor : " + scale * scaleFactor);

            RectF rect = getImageRectF();

//            float scaleX = rect.right - rect.left > getWidth() ? detector.getFocusX() : getWidth() / 2;
//            float scaleY = rect.bottom - rect.top > getHeight() ? detector.getFocusY() : getHeight() / 2;

//            Log.i(TAG, "scaleX: " + scaleX + "\t scaleY：" + scaleY);
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
//            Log.d(TAG, "onScale: " + detector.getCurrentSpanX() + "\t : " + detector.getPreviousSpanX());
//            if (detector.getCurrentSpan() < detector.getPreviousSpan()) {
                correctRect();
//            }
            setImageMatrix(mScaleMatrix);

        }
        return true;
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

        float width = getWidth();
        float height = getHeight();

        Log.d(TAG, "Rect: " + rect);
        if (rect.width() > getWidth()) {
            if ((rect.left > 0)) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
            Log.i(TAG, "修正X：" + deltaX);
        }

        if (rect.height() > getHeight()) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
            Log.i(TAG, "修正Y：" + deltaY);
        }
        if (rect.width() < width) {
            deltaX = width / 2.f - rect.right + rect.width() / 2.f;
            Log.i(TAG, "\t偏移X: " + deltaX);
        }
        if (rect.height() < height) {
            deltaY = height / 2.f - rect.bottom + rect.height() / 2.f;
            Log.i(TAG, "\t偏移Y " + deltaY);
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Log.d(TAG, "onScaleBegin: ");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        float scale = getScaleSize();
        if (scale < mMinScaleSize) {
            Log.w(TAG, "onScaleEnd: ");
            //属性动画传递的是当前进度，其实应该再估值器中计算正确的值
            ObjectAnimator.ofFloat(this, "scaleSize", 1.f)
                    .setDuration(500)
                    .start();
        }
    }

    /**
     * 在Traversal之后会被回调
     */
    @Override
    public void onGlobalLayout() {
        initLayout();
    }

    public void setScaleSize(float percent) {
        float scale = getScaleSize();
        //根据时间进度计算缩放大小
        float finalScale = ((mMinScaleSize - scale) * percent + scale) / scale;
        mScaleMatrix.postScale(finalScale, finalScale, getWidth() / 2, getHeight() / 2);
        setImageMatrix(mScaleMatrix);
    }

    public float getScaleSize() {
        mScaleMatrix.getValues(mMatrixValues);
        return mMatrixValues[Matrix.MSCALE_X] < mMatrixValues[Matrix.MSCALE_Y] ?
                mMatrixValues[Matrix.MSCALE_X] : mMatrixValues[Matrix.MSCALE_Y];
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
