package com.doing.material.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doing.material.R;

public class AnimateViewActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, AnimateViewActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_view);

        final Button button = (Button) findViewById(R.id.btn_start);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable background = button.getBackground();
                if (background instanceof Animatable) {
                    ((Animatable) background).start();
                }
            }
        });
    }
}
