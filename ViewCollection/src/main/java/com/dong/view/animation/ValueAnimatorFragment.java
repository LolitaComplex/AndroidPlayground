package com.dong.view.animation;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.dong.lib.utils.LogUtils;
import com.dong.view.BaseFragment;

/**
 * Created by 杜营 on 2016/6/13.
 *
 */
public class ValueAnimatorFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View freelyFallingBall = drawCircle();
        View parabolaBall = drawCircle();
        FrameLayout layout = new FrameLayout(mContext);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(layoutParams);

        LayoutParams subLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layout.addView(freelyFallingBall,subLayoutParams);
        layout.addView(parabolaBall,subLayoutParams);

        initFreelyFallAnimator(freelyFallingBall);
        initParabolaAnimator(parabolaBall);
        return layout;
    }

    private View drawCircle(){
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas convas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        convas.drawCircle(50, 50, 40, paint);

        ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(bitmap);

        return imageView;
    }

    //自由落体小球
    private void initFreelyFallAnimator(final View view) {

        view.post(new Runnable() {
            @Override
            public void run() {
                Display defaultDisplay = mContext.getWindowManager().getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getSize(point);
                int screenHeight = point.y;
                ValueAnimator animator = ValueAnimator.ofFloat(0, screenHeight - view.getHeight() - 80);
                animator.setTarget(view);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(3000).start();
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        view.setTranslationY(value);
                    }
                });

                LogUtils.e(TAG, screenHeight - view.getHeight() + "");
            }
        });
    }

    //抛物线运动的小球
    private void initParabolaAnimator(final View view){
        //方法1
        ValueAnimator animator = ValueAnimator.ofFloat(0,3.0f);
        animator.setTarget(view);
        animator.setDuration(3000).start();
        //final LayoutParams layoutParams = view.getLayoutParams();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float time = (float) animation.getAnimatedValue();
                view.setTranslationX(300 * time);
                view.setTranslationY(0.5f*355*time*time);
                //layoutParams.height = (int) (0.5f*355*time*time);//能够控制控件的高，来达到放大或者叫卷轴打开的效果
                //view.setLayoutParams(layoutParams);
            }
        });

        //方法2
        ValueAnimator animator2 = new ValueAnimator();
        animator2.setDuration(3000);
        animator2.setObjectValues(new PointF());
        animator2.setEvaluator(new TypeEvaluator<PointF>() {

            //fraction = time / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                LogUtils.e(TAG, fraction * 3 + "");
                float time = fraction * 3;
                PointF point = new PointF();
                point.x = 300 * time;
                point.y = 0.5f * 355 * time * time;
                return point;
            }
        });
        animator2.setTarget(view);
        animator2.setStartDelay(3000);
        animator2.start();
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();

                view.setX(point.x);
                view.setY(point.y);
            }
        });
    }
}
