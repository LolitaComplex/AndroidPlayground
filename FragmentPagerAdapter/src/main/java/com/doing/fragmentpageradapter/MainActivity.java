package com.doing.fragmentpageradapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.doing.fragmentpageradapter.ui.activity.CyclePagerActivity;
import com.doing.fragmentpageradapter.ui.activity.PagerActivity;
import com.doing.fragmentpageradapter.ui.activity.StatePagerActivity;
import com.doing.fragmentpageradapter.ui.activity.TouchImageViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("MainPager");
    }

    public void onClickPager(View view) {
        PagerActivity.start(this);
    }

    public void onClickStatePager(View view) {
        StatePagerActivity.start(this);
    }

    public void onClickCyclePager(View view) {
        CyclePagerActivity.start(this);
    }

    public void onClickTouchImageView(View view) {
        TouchImageViewActivity.start(this);
    }
}
