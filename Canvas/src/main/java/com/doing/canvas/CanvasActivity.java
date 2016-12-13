package com.doing.canvas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.doing.canvas.activity.PixelColorCanvasActivity;
import com.doing.canvas.activity.TableCanvasActivity;

import butterknife.ButterKnife;

public class CanvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        ButterKnife.findById(this,R.id.CanvasActivity_table)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TableCanvasActivity.start(CanvasActivity.this);
                    }
                });

        ButterKnife.findById(this, R.id.CanvasActivity_color)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PixelColorCanvasActivity.start(
                                CanvasActivity.this);
                    }
                });
    }
}
