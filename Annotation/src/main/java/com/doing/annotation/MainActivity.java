package com.doing.annotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doing.annotation.ioc.IocActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnIocViewInject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnIocViewInject = ((Button) findViewById(R.id.MainActivity_btn_ioc));

        mBtnIocViewInject.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MainActivity_btn_ioc:
                IocActivity.start(MainActivity.this);
                break;
        }
    }
}
