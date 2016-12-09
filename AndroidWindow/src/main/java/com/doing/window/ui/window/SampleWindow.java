package com.doing.window.ui.window;


import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class SampleWindow {

    private final WindowManager mWindowManager;
    private final Context mContext;
    private FrameLayout decorView;

    public SampleWindow(Context context) {
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public void create(int width, int height) {
        decorView = new DecordView(mContext);
        decorView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("SampleWindow", "按下按键！！");
                return false;
            }
        });
        mWindowManager.addView(decorView, createWindowLayoutParams(width, height));
    }

    public void setContent(View content) {
        decorView.addView(content);
    }

    private WindowManager.LayoutParams createWindowLayoutParams(int width, int height) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        params.format = PixelFormat.RGBA_8888;
        params.gravity = Gravity.LEFT;
        params.width = width;
        params.height = height;
        params.x = 0;
        params.y = 0;
        params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;


        return params;
    }

    public void move(int moveX, int moveY) {
        WindowManager.LayoutParams params =
                (WindowManager.LayoutParams) decorView.getLayoutParams();
        params.x += moveX;
        params.y += moveY;
        mWindowManager.updateViewLayout(decorView, params);
    }

    public void remveView() {
        mWindowManager.removeView(decorView);
    }

    private class DecordView extends FrameLayout {

        public DecordView(Context context) {
            super(context);
        }

        public DecordView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public DecordView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                remveView();
            }
            return super.dispatchKeyEvent(event);
        }
    }
}
