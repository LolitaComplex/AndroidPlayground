package com.netease.study.ui.surface.activity.finite;

import android.view.SurfaceView;

import com.netease.study.ui.surface.R;
import com.netease.study.ui.surface.finite.FiniteFramePlayer;
import com.netease.study.ui.surface.finite.FiniteFrameRenderer;
import com.netease.study.ui.surface.exec.HandlerExecutor;
import com.netease.study.ui.surface.base.PlayExecutor;
import com.netease.study.ui.surface.activity.finite.renderer.ColorsRenderer;
import com.netease.study.ui.surface.view.SurfaceVideoView;

public class RenderColorsActivity extends FiniteFrameActivity<SurfaceView> {
    @Override
    protected FiniteFramePlayer onCreatePlayer(SurfaceView surfaceView) {
        FiniteFrameRenderer renderer = new ColorsRenderer(getResources().getStringArray(R.array.colors), 4);
        PlayExecutor executor = new HandlerExecutor();

        return new FiniteFramePlayer(new SurfaceVideoView(surfaceView.getHolder()),
                renderer, executor, renderer.getCount());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_finite_frame_surface;
    }
}
