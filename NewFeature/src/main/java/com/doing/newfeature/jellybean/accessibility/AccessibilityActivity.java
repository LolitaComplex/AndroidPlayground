package com.doing.newfeature.jellybean.accessibility;

import android.animation.Animator;
import android.animation.TimeAnimator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

import com.doing.newfeature.R;

import java.util.List;

public class AccessibilityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);

//        AccessibilityRecordCompat accessibilityRecord = AccessibilityEventCompat.asRecord();

        AccessibilityEvent obtain = AccessibilityEvent.obtain();
        AccessibilityNodeInfo source = obtain.getSource();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TimeAnimator animator = new TimeAnimator();
            animator.setTimeListener(new TimeAnimator.TimeListener() {
                @Override
                public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {

                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                animator.addPauseListener(new Animator.AnimatorPauseListener() {
                    @Override
                    public void onAnimationPause(Animator animation) {

                    }

                    @Override
                    public void onAnimationResume(Animator animation) {

                    }
                });
            }
            animator.start();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private class AccessibilityProvider extends AccessibilityNodeProvider{

        public AccessibilityProvider() {
            super();
        }

        @Override
        public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
            return super.createAccessibilityNodeInfo(virtualViewId);
        }

        @Override
        public boolean performAction(int virtualViewId, int action, Bundle arguments) {
            return super.performAction(virtualViewId, action, arguments);
        }

        @Override
        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
            return super.findAccessibilityNodeInfosByText(text, virtualViewId);
        }

        @Override
        public AccessibilityNodeInfo findFocus(int focus) {
            return super.findFocus(focus);
        }
    }
}
