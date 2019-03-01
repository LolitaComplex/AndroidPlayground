package com.netease.study.ui.surface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.netease.study.ui.surface.activity.finite.RenderAlphaActivity;
import com.netease.study.ui.surface.activity.finite.RenderAlphaTextureActivity;
import com.netease.study.ui.surface.activity.finite.RenderCirclesActivity;
import com.netease.study.ui.surface.activity.finite.RenderCirclesTextureActivity;
import com.netease.study.ui.surface.activity.finite.RenderColorsActivity;
import com.netease.study.ui.surface.activity.finite.RenderColorsTextureActivity;
import com.netease.study.ui.surface.activity.finite.RenderSquaresActivity;
import com.netease.study.ui.surface.activity.finite.RenderSquaresTextureActivity;
import com.netease.study.ui.surface.activity.infinite.RenderPathActivity;
import com.netease.study.ui.surface.activity.infinite.RenderPathTextureActivity;

public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.render_colors).setOnClickListener(this);
        findViewById(R.id.render_alpha).setOnClickListener(this);
        findViewById(R.id.render_squares).setOnClickListener(this);
        findViewById(R.id.render_circles).setOnClickListener(this);
        findViewById(R.id.render_path).setOnClickListener(this);

        findViewById(R.id.render_texture_colors).setOnClickListener(this);
        findViewById(R.id.render_texture_alpha).setOnClickListener(this);
        findViewById(R.id.render_texture_squares).setOnClickListener(this);
        findViewById(R.id.render_texture_circles).setOnClickListener(this);
        findViewById(R.id.render_texture_path).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.render_colors: {
                startActivity(new Intent(this, RenderColorsActivity.class));
                break;
            }
            case R.id.render_alpha: {
                startActivity(new Intent(this, RenderAlphaActivity.class));
                break;
            }
            case R.id.render_squares: {
                startActivity(new Intent(this, RenderSquaresActivity.class));
                break;
            }
            case R.id.render_circles: {
                startActivity(new Intent(this, RenderCirclesActivity.class));
                break;
            }
            case R.id.render_path: {
                startActivity(new Intent(this, RenderPathActivity.class));
                break;
            }
            case R.id.render_texture_colors: {
                startActivity(new Intent(this, RenderColorsTextureActivity.class));
                break;
            }
            case R.id.render_texture_alpha: {
                startActivity(new Intent(this, RenderAlphaTextureActivity.class));
                break;
            }
            case R.id.render_texture_squares: {
                startActivity(new Intent(this, RenderSquaresTextureActivity.class));
                break;
            }
            case R.id.render_texture_circles: {
                startActivity(new Intent(this, RenderCirclesTextureActivity.class));
                break;
            }
            case R.id.render_texture_path: {
                startActivity(new Intent(this, RenderPathTextureActivity.class));
                break;
            }

        }
    }
}
