package com.doing.annotation.ioc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.doing.annotation.R;

@ContentView(R.layout.activity_ioc)
public class IocActivity extends AppCompatActivity {

    @ViewInject(R.id.IocActivity_tv_text1)
    protected TextView mTvText1;
    @ViewInject(R.id.IocActivity_tv_text2)
    protected TextView mTvText2;
    @ViewInject(R.id.IocActivity_tv_text3)
    protected TextView mTvText3;

    public static void start(Context context) {
        context.startActivity(new Intent(context, IocActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);

        mTvText3.setText("反射注解真的很简单呢");

    }

    @OnClick({R.id.IocActivity_btn1, R.id.IocActivity_btn2, R.id.IocActivity_btn3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IocActivity_btn1:
                Toast.makeText(IocActivity.this, "Button1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.IocActivity_btn2:
                Toast.makeText(IocActivity.this, "Button2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.IocActivity_btn3:
                Toast.makeText(IocActivity.this, "Button3", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
