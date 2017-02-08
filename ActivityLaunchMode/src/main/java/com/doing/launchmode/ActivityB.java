package com.doing.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static android.R.attr.onClick;


/**
 * Created by Administrator on 2016/11/30.
 */
public class ActivityB extends AppCompatActivity {

    public static final int INT = 3;
    public static final int ASD = 12819084;
    private String mName;
    private String mage;
    private boolean msex;


    private boolean mFlag = true;
    private int mMyAgea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        getWindow().getDecorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mMyAgea = INT;

        int i = ASD;

        switch (mage) {

            case "1":

                break;
            case "3":

                break;
            default:

                break;
        }
    }

    public void Start(View view) {
        Class clazz;
        if (mFlag) {
            clazz = ActivityC.class;
        } else {
            clazz = ActivityD.class;
        }
        startActivity(new Intent(this, clazz));
    }



}
