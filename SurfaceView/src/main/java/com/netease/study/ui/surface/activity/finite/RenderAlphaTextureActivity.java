package com.netease.study.ui.surface.activity.finite;

import android.graphics.Color;
import android.util.Log;
import android.view.TextureView;
import android.widget.Toast;

import com.netease.study.ui.surface.R;
import com.netease.study.ui.surface.activity.finite.renderer.AlphaRenderer;
import com.netease.study.ui.surface.base.PlayExecutor;
import com.netease.study.ui.surface.exec.HandlerExecutor;
import com.netease.study.ui.surface.finite.FiniteFramePlayer;
import com.netease.study.ui.surface.finite.FiniteFrameRenderer;
import com.netease.study.ui.surface.view.TextureVideoView;

public class RenderAlphaTextureActivity extends FiniteFrameActivity<TextureView> {
    @Override
    protected FiniteFramePlayer onCreatePlayer(TextureView textureView) {
        textureView.setBackgroundColor(Color.BLUE);
        textureView.setOpaque(true);
        boolean hardwareAccelerated = textureView.isHardwareAccelerated();
        Toast.makeText(this, hardwareAccelerated + "", Toast.LENGTH_LONG).show();

        mContainer.setBackgroundColor(Color.BLUE);


        FiniteFrameRenderer renderer = new AlphaRenderer();
        PlayExecutor executor = new HandlerExecutor();

        return new FiniteFramePlayer(new TextureVideoView(textureView),
                renderer, executor, renderer.getCount());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_finite_frame_texture;
    }
}
