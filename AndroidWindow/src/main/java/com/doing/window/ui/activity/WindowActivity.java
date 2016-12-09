package com.doing.window.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.doing.window.R;
import com.doing.window.ui.controll.DecorViewController;
import com.doing.window.ui.window.SimpleWindow;

import butterknife.ButterKnife;

public class WindowActivity extends AppCompatActivity implements View.OnClickListener {

    private DecorViewController mController;
    private SimpleWindow mSimpleWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);

        ButterKnife.findById(this, R.id.WindowActivity_addView).setOnClickListener(this);
        ButterKnife.findById(this, R.id.WindowActivity_removeView).setOnClickListener(this);
        ButterKnife.findById(this, R.id.WindowActivity_setFrontBg).setOnClickListener(this);
        ButterKnife.findById(this, R.id.WindowActivity_resetFrontBg).setOnClickListener(this);
        ButterKnife.findById(this, R.id.WindowActivity_setBgColor).setOnClickListener(this);
        ButterKnife.findById(this, R.id.WindowActivity_productDialog).setOnClickListener(this);

        mController = new DecorViewController(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.WindowActivity_setFrontBg:
                mController.setForeground();
                break;
            case R.id.WindowActivity_resetFrontBg:
                mController.resetForeground();
                break;
            case R.id.WindowActivity_addView:
                mController.addView();
                break;

            case R.id.WindowActivity_removeView:
                mController.removeView();
                break;

            case R.id.WindowActivity_setBgColor:
                mController.setBgColor();
                break;

            case R.id.WindowActivity_productDialog:
                mSimpleWindow = new SimpleWindow(this);
                mSimpleWindow.start(600, 1000);
                break;
        }
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public void onBackPressed() {
        Log.e("WindowActivity", "返回键按下了");

        if (mController.isMaskStart()) {
            mController.removeView();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("WindowActivity", "按键按下了" + keyCode);
        return super.onKeyDown(keyCode, event);
    }
}
