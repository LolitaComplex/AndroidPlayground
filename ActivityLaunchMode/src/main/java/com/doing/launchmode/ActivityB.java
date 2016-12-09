package com.doing.launchmode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * Created by Administrator on 2016/11/30.
 */
public class ActivityB extends AppCompatActivity {

    private boolean mFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        System.runFinalization();

    }

    public void Start(View view) {
        Class clazz;
        if (mFlag) {
            clazz = ActivityC.class;
        } else {
            clazz = ActivityD.class;
        }
    }

}
