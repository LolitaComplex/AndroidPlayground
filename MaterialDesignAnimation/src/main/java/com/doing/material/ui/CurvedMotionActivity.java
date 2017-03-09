package com.doing.material.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.PathInterpolator;

import com.doing.material.R;

public class CurvedMotionActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, CurvedMotionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curved_motion);

        initCurvedMotion();

        initDraw();

    }

    private void initDraw() {
    }

    private void initCurvedMotion() {
        View ball = findViewById(R.id.ball);

        //运动路径
        Path path = new Path();
        path.lineTo(100, 300);
        path.cubicTo(200, 200, 300, 200, 400, 600);
        path.cubicTo(500, 400, 550, 400, 600, 600);

        //用曲线坐标轴的方式控制速度。起始点(0, 0)与终点(1,1)坐标已经预设完毕，我们填写的中间点，来让速度变化是否是线性
        PathInterpolator pathInterpolator = new PathInterpolator(.2f, 0f, 1f, 1f);
        final ObjectAnimator anim = ObjectAnimator.ofFloat(ball, View.X, View.Y, path);
        anim.setInterpolator(pathInterpolator);
        anim.setDuration(6000);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim.start();
            }
        });
    }
}
