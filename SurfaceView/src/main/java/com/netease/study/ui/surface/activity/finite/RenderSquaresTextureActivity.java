package com.netease.study.ui.surface.activity.finite;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.SurfaceView;
import android.view.TextureView;

import com.netease.study.ui.surface.R;
import com.netease.study.ui.surface.activity.finite.renderer.SquaresRenderer;
import com.netease.study.ui.surface.base.PlayExecutor;
import com.netease.study.ui.surface.exec.HandlerExecutor;
import com.netease.study.ui.surface.finite.FiniteFramePlayer;
import com.netease.study.ui.surface.finite.FiniteFrameRenderer;
import com.netease.study.ui.surface.view.SurfaceVideoView;
import com.netease.study.ui.surface.view.TextureVideoView;

public class RenderSquaresTextureActivity extends FiniteFrameActivity<TextureView> {
    @Override
    protected FiniteFramePlayer onCreatePlayer(TextureView textureView) {
        FiniteFrameRenderer renderer = new SquaresRenderer(5, 5);

        HandlerThread thread = new HandlerThread("render");
        thread.start();
        PlayExecutor executor = new HandlerExecutor(new Handler(thread.getLooper()));

        return new FiniteFramePlayer(new TextureVideoView(textureView),
                renderer, executor, renderer.getCount());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_finite_frame_texture;
    }
}
