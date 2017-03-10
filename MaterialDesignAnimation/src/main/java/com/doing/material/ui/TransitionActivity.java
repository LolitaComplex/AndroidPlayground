package com.doing.material.ui;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;

import com.doing.material.R;

public class TransitionActivity extends AppCompatActivity {

    private AppBarLayout mAppBar;
    private FloatingActionButton mFab;

    public static void start(Context context) {
        context.startActivity(new Intent(context, TransitionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTheme().applyStyle(R.style.NoActionBarTheme, true);
        setContentView(R.layout.activity_transition);

        Toolbar toolbar = (Toolbar) findViewById(R.id.TransitionActivity_toolbar);
        setSupportActionBar(toolbar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //方式1，如果手机是5.0以上手机，其实默认Transition就是启动的
//            getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
//            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        mAppBar = (AppBarLayout) findViewById(R.id.TransitionActivity_appbar);
        mFab = (FloatingActionButton) findViewById(R.id.activity_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 默认Transition动画是全屏渐显跳转
                 */
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(TransitionActivity.this,
                            TargetTransitionActivity.class);
                    ActivityOptions activityOptions = ActivityOptions
                            .makeSceneTransitionAnimation(TransitionActivity.this,
                                    Pair.create((View) mFab, "fabButton"),
                                    Pair.create((View) mAppBar, "appbar"));
                    startActivity(intent, activityOptions.toBundle());
                }
            }
        });
    }

    public void onBox1Click(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionSet exitTransition = new TransitionSet();
            exitTransition.excludeTarget(android.R.id.navigationBarBackground, true);
            exitTransition.excludeTarget(android.R.id.statusBarBackground, true);
            exitTransition.excludeTarget(R.id.TransitionActivity_appbar, true);
            exitTransition.excludeTarget(R.id.TransitionActivity_toolbar, true);
            exitTransition.excludeTarget(R.id.activity_fab, true);

            exitTransition.addTransition(new Fade());
            Transition otherTransition = new Slide(Gravity.BOTTOM);
            exitTransition.addTransition(otherTransition);
            exitTransition.setDuration(500);
            //虽然只设置了ExitTransition，不过ReenterTransition也同时被设置了逆动画
            getWindow().setExitTransition(exitTransition);
        }

    }

    public void onBox2Click(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.RIGHT);
            slide.excludeTarget(android.R.id.navigationBarBackground, true);
            slide.excludeTarget(android.R.id.statusBarBackground, true);
            slide.excludeTarget(R.id.TransitionActivity_appbar, true);
            slide.excludeTarget(R.id.TransitionActivity_toolbar, true);
            getWindow().setExitTransition(slide);
        }
    }

    public void onBox3Click(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTransition(new Fade());
        }
    }

    public void onBox4Click(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTransition(new Explode());
        }
    }

    public void onBox5Click(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTransition(new Slide(Gravity.LEFT));
        }
    }

    public void onBox6Click(View view) {

    }

    private void setTransition(Transition transition) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transition.excludeTarget(android.R.id.navigationBarBackground, true);
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            transition.excludeTarget(R.id.TransitionActivity_appbar, true);
            transition.excludeTarget(R.id.TransitionActivity_toolbar, true);
            getWindow().setReenterTransition(transition);
        }
    }
}
