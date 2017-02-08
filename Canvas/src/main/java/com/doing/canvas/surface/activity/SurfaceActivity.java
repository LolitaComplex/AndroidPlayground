package main.java.com.doing.canvas.surface.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.doing.canvas.R;

import butterknife.ButterKnife;

public class SurfaceActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, SurfaceActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface);

        ButterKnife.findById(this, R.id.CanvasActivity_colors_frames)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ColorsFrameActivity.start(SurfaceActivity.this);
                    }
                });
    }
}
