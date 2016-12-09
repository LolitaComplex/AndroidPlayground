package com.doing.window.ui.window;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.doing.window.R;

public class SimpleWindow implements View.OnClickListener {

    private final SampleWindow mWindow;
    private final Context mContext;

    public SimpleWindow(Context context) {
        mContext = context;
        mWindow = new SampleWindow(context);
    }

    public void start(int width, int height) {
        mWindow.create(width, height);
        mWindow.setContent(getContentView());
    }

    private View getContentView() {
        RelativeLayout inflate = (RelativeLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.layout_masking, null);

        inflate.findViewById(R.id.Layout_btn1).setOnClickListener(this);
        inflate.findViewById(R.id.Layout_btn2).setOnClickListener(this);
        inflate.findViewById(R.id.Layout_btn3).setOnClickListener(this);
        inflate.findViewById(R.id.Layout_btn4).setOnClickListener(this);
        inflate.findViewById(R.id.Layout_btn5).setOnClickListener(this);

        inflate.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("RelativeLayout", "按下按键！！");
                return false;
            }
        });


        return inflate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Layout_btn1:
                mWindow.move(0, 20);
                break;
            case R.id.Layout_btn2:
                mWindow.move(20, 0);
                break;
            case R.id.Layout_btn3:
                mWindow.move(-20, 0);
                break;
            case R.id.Layout_btn5:
                mWindow.move(0, -20);
                break;
            case R.id.Layout_btn4:
                Toast.makeText(mContext, "中间被点击了", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void removeView() {
        mWindow.remveView();
    }
}
