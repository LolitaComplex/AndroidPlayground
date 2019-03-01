package com.netease.study.ui.surface.activity.finite;

import android.view.TextureView;

import com.netease.study.ui.surface.R;
import com.netease.study.ui.surface.activity.finite.renderer.ColorsRenderer;
import com.netease.study.ui.surface.base.PlayExecutor;
import com.netease.study.ui.surface.exec.HandlerExecutor;
import com.netease.study.ui.surface.finite.FiniteFramePlayer;
import com.netease.study.ui.surface.finite.FiniteFrameRenderer;
import com.netease.study.ui.surface.view.TextureVideoView;

public class RenderColorsTextureActivity extends FiniteFrameActivity<TextureView> {
    @Override
    protected FiniteFramePlayer onCreatePlayer(TextureView textureView) {
        FiniteFrameRenderer renderer = new ColorsRenderer(getResources()
                .getStringArray(R.array.colors), 4);
        PlayExecutor executor = new HandlerExecutor();

        return new FiniteFramePlayer(new TextureVideoView(textureView),
                renderer, executor, renderer.getCount());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_finite_frame_texture;
    }
}
