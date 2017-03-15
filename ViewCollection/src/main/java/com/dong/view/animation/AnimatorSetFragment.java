package com.dong.view.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dong.lib.utils.LogUtils;
import com.dong.lib.utils.UIUtils;
import com.dong.view.BaseFragment;

/**
 * Created by 杜营 on 2016/6/14.
 *
 */
public class AnimatorSetFragment extends BaseFragment {

    private Button mTogether;
    private Button mInProperOrder;
    private View ball;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView();
        setListener();
        return view;
    }

    private void setListener() {
        mTogether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togetherRun();
            }
        });
        mInProperOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inProperOrder();
            }
        });
    }

    private void togetherRun() {
        Rect windowSize = UIUtils.getWindowSize();
        ObjectAnimator tranXAnimator = ObjectAnimator.ofFloat(ball, "translationX", 0, windowSize.right - 300);
        ObjectAnimator tranYAnimator = ObjectAnimator.ofFloat(ball, "translationY", 0, windowSize.bottom - 500);

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(ball, "scaleX", 1, 0.5f, 3.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(ball,"scaleY",1,0.5f,3.0f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000);
        set.playTogether(tranXAnimator, tranYAnimator, scaleXAnimator, scaleYAnimator);
//        set.playSequentially(tranXAnimator,tranYAnimator,scaleXAnimator,scaleYAnimator);
        set.start();
    }

    private void inProperOrder() {
        Rect windowSize = UIUtils.getWindowSize();
        ObjectAnimator tranXAnimator = ObjectAnimator.ofFloat(ball, "translationX", 0, windowSize.right - 300);
        ObjectAnimator tranYAnimator = ObjectAnimator.ofFloat(ball, "translationY", 0, windowSize.bottom - 500);

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(ball, "scaleX", 1, 0.5f, 3.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(ball,"scaleY",1,0.5f,3.0f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000);
        set.play(tranXAnimator).with(scaleXAnimator);
        set.play(tranYAnimator).after(scaleXAnimator);
        set.play(tranYAnimator).with(scaleYAnimator);
        set.start();
    }


    private View initView() {
        FrameLayout layout = new FrameLayout(mContext);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
        mTogether = makeButton("一起执行");
        mInProperOrder = makeButton("依指定顺序执行");
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.addView(mTogether);
        linearLayout.addView(mInProperOrder);
        layout.addView(linearLayout,layoutParams);

        ball = drawCircle();
        layout.addView(ball);
        return layout;
    }

    private Button makeButton(String text){
        Button button = new Button(mContext);
        button.setText(text);
        button.setTextSize(15);
        button.setPadding(50, 5, 50, 5);
        button.setGravity(Gravity.CENTER);
        return button;
    }

    private View drawCircle(){
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas convas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new RadialGradient(50, 50, 40, Color.WHITE, Color.BLUE, Shader.TileMode.MIRROR));
        convas.drawCircle(50, 50, 40, paint);

        ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(bitmap);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return imageView;
    }
}

/*
 ShapeDrawable shapeDrawable = new ShapeDrawable();
        Shape shape = new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                paint.setAntiAlias(true);
                paint.setColor(Color.GREEN);
                canvas.drawCircle(50, 50, 40, paint);
            }
        };
        shapeDrawable.setShape(shape);
        shapeDrawable.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new RadialGradient(50, 50, 40, Color.WHITE, Color.GREEN, Shader.TileMode.MIRROR);
            }
        });

    ImageView imageView = new ImageView(mContext);
    imageView.setImageDrawable(shapeDrawable);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
* */
