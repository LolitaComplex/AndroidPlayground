package com.doing.material.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;

import com.doing.material.R;

public class RevealEffectActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, RevealEffectActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal_effect);

        final View rootView = getWindow().getDecorView();

        final View box1 = findViewById(R.id.box1);
        final View box2 = findViewById(R.id.box2);
        final View box3 = findViewById(R.id.box3);
        final View box4 = findViewById(R.id.box4);

        Button btnIn = (Button) findViewById(R.id.btn_in);
        Button btnOut = (Button) findViewById(R.id.btn_out);

        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effectBox(true, box1, box2, box3, box4);
            }
        });

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effectBox(false, box1, box2, box3, box4);
            }
        });

        box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRevealEffect(rootView, Color.parseColor("#FF6666"), true);
            }
        });

        box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRevealEffect(rootView, Color.parseColor("#66FF66"), true);
            }
        });

        box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRevealEffect(rootView, Color.parseColor("#66CCFF"), true);
            }
        });

        box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRevealEffect(rootView, Color.parseColor("#FFFF00"), true);
            }
        });

        ObjectAnimator.ofFloat(rootView, View.X, View.Y, new Path());
    }

    private void effectBox(boolean visible, View... views) {
        for(View view : views){
            initRevealEffect(view, visible);
        }
    }

    private void initRevealEffect(final View view, final boolean visible) {
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        int circleX = rect.centerX();
        int circleY = rect.centerY();

        int[] radius = getRevealRadius(view, visible);
        Animator animator = ViewAnimationUtils.createCircularReveal(view, circleX, circleY, radius[0], radius[1]);
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (visible) {
                    view.setVisibility(view.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
            }
        });
        animator.start();
    }

    private int[] getRevealRadius(View view, boolean visible) {
        int[] result = new int[2];
        result[0] = visible ? 0 : view.getHeight();
        result[1] = !visible ? 0 : view.getHeight();
        return result;
    }

    private int flag = 0;

    private void initRevealEffect(View view, int color, boolean visible) {
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        int circleX;
        int circleY;
        switch (flag) {
            case 0 :
                circleX = rect.left + 30;
                circleY = rect.top + 30;
                break;

            case 1 :
                circleX = rect.right - 30;
                circleY = rect.bottom - 30;
                break;

            case 2 :
                circleX = rect.left + 30;
                circleY = rect.bottom - 30;
                break;

            case 3 :
                circleX = rect.right - 30;
                circleY = rect.top + 30;
                break;
            default:
                circleX = rect.centerX();
                circleY = rect.centerY();
                color = Color.GRAY;
                break;
        }
        view.setBackgroundColor(color);

        int[] radius = getRevealRadius(view, visible);
        Animator animator = ViewAnimationUtils.createCircularReveal(view, circleX, circleY, radius[0], radius[1]);
        animator.setDuration(500);
        animator.start();

        flag = (++flag) % 5;
    }

}
