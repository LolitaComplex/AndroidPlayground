package com.doing.loader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.doing.loader.ui.AsyncActivity;
import com.doing.loader.ui.BindingLoaderActivity;
import com.doing.loader.ui.CursorActivity;

import butterknife.ButterKnife;

public class LoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        ButterKnife.findById(this, R.id.LoaderActivity_btn_async)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AsyncActivity.start(LoaderActivity.this);
                    }
                });

        ButterKnife.findById(this, R.id.LoaderActivity_btn_cursor)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CursorActivity.start(LoaderActivity.this);
                    }
                });

        ButterKnife.findById(this, R.id.LoaderActivity_btn_binding)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BindingLoaderActivity.start(LoaderActivity.this);
                    }
                });
    }
}
