package com.doing.material.ui;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.doing.material.R;

public class TargetTransitionActivity extends AppCompatActivity {

    private LinearLayout mContentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTheme().applyStyle(R.style.NoActionBarTheme, true);
        setContentView(R.layout.activity_target_transition);

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            Slide slideTransition = new Slide(Gravity.BOTTOM);
//            slideTransition.excludeTarget(android.R.id.statusBarBackground, true);
//            slideTransition.excludeTarget(android.R.id.navigationBarBackground, true);
//            slideTransition.excludeTarget(R.id.TargetTransitionActivity_appbar, true);
//            slideTransition.excludeTarget(R.id.TargetTransitionActivity_toolbar, true);
//            slideTransition.setDuration(500);
//            getWindow().setEnterTransition(slideTransition);
//        }

        mContentGroup = ((LinearLayout) findViewById(R.id.TargetTransitionActivity_ll));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {

                @Override
                public void onTransitionStart(Transition transition) {}

                //在入场Transition之后
                @Override
                public void onTransitionEnd(Transition transition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        //良好习惯
                        getWindow().getEnterTransition().removeListener(this);

                        for (int i = 0; i < mContentGroup.getChildCount(); i++) {
                            View view = mContentGroup.getChildAt(i);
                            ViewCompat.animate(view).scaleX(1).scaleY(1).setStartDelay(i * 30);
                        }
                    }
                }

                @Override
                public void onTransitionCancel(Transition transition) {}

                @Override
                public void onTransitionPause(Transition transition) {}

                @Override
                public void onTransitionResume(Transition transition) {}
            });
        }
    }

    @Override
    public void onBackPressed() {
        for (int i = 0; i < mContentGroup.getChildCount(); i++) {
            View view = mContentGroup.getChildAt(i);
            ViewCompat.animate(view)
                    .scaleX(0)
                    .scaleY(0)
                    .setStartDelay(i * 30)
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {}

                        @Override
                        public void onAnimationEnd(View view) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                finishAfterTransition();
                            }
                        }

                        @Override
                        public void onAnimationCancel(View view) {}
                    })
                    .start();
        }
    }
}
