package com.netease.study.ui.surface.activity.finite;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.SurfaceView;

import com.netease.study.ui.surface.R;
import com.netease.study.ui.surface.activity.finite.renderer.AlphaRenderer;
import com.netease.study.ui.surface.base.PlayExecutor;
import com.netease.study.ui.surface.exec.HandlerExecutor;
import com.netease.study.ui.surface.finite.FiniteFramePlayer;
import com.netease.study.ui.surface.finite.FiniteFrameRenderer;
import com.netease.study.ui.surface.view.SurfaceVideoView;

public class RenderAlphaActivity extends FiniteFrameActivity<SurfaceView> {
    @Override
    protected FiniteFramePlayer onCreatePlayer(SurfaceView surfaceView) {
        surfaceView.setBackgroundColor(Color.BLUE);
        surfaceView.setZOrderOnTop(true);
        surfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);

        FiniteFrameRenderer renderer = new AlphaRenderer();
        PlayExecutor executor = new HandlerExecutor();

        return new FiniteFramePlayer(new SurfaceVideoView(surfaceView.getHolder()),
                renderer, executor, renderer.getCount());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_finite_frame_surface;
    }
}
