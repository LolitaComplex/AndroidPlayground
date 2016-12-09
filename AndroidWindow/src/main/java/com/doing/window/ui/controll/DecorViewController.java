package com.doing.window.ui.controll;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.doing.window.R;

import java.util.Random;

public class DecorViewController implements View.OnClickListener {

    private Activity mActivity;
    private FrameLayout mDecorView;
    private boolean mIsMaskingStart;
    private Random random;

    public DecorViewController(Activity activity) {
        this.mActivity = activity;
        mDecorView = getDecorView();
    }

    public void setForeground() {
        Drawable drawable = ContextCompat.getDrawable(mActivity, R.drawable.decorbackground);
        mDecorView.setForeground(drawable);
    }

    public void resetForeground() {
        mDecorView.setForeground(null);
    }

    public void addView() {
        mIsMaskingStart = true;
        mDecorView.addView(initMasking());
    }

    public void removeView() {
        mIsMaskingStart = false;
        mDecorView.removeViewAt(mDecorView.getChildCount() - 1);
    }

    public void setBgColor() {
        int childCount = mDecorView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            mDecorView.getChildAt(i).setBackgroundColor(initRandomColor());
        }

    }

    public boolean isMaskStart() {
        return mIsMaskingStart;
    }

    private FrameLayout getDecorView() {
        View decorView = mActivity.getWindow().getDecorView();

        if (decorView instanceof FrameLayout) {
            return (FrameLayout) decorView;
        }

        return null;
    }

    private View initMasking() {
        View inflate = LayoutInflater.from(mActivity)
                .inflate(R.layout.layout_masking, mDecorView, false);

        inflate.findViewById(R.id.Layout_btn1).setOnClickListener(this);
        inflate.findViewById(R.id.Layout_btn2).setOnClickListener(this);
        inflate.findViewById(R.id.Layout_btn3).setOnClickListener(this);
        inflate.findViewById(R.id.Layout_btn4).setOnClickListener(this);
        inflate.findViewById(R.id.Layout_btn5).setOnClickListener(this);

        inflate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return inflate;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(mActivity, ((Button) v).getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private int initRandomColor() {
        if (random == null) {
            random = new Random();
        }
        int a = random.nextInt(225);
        int r = random.nextInt(225);
        int g = random.nextInt(225);
        int b = random.nextInt(225);
        return Color.argb(a, r, g, b);
    }
}
