package com.netease.study.ui.surface.infinite;

import com.netease.study.ui.surface.base.Frame;
import com.netease.study.ui.surface.base.FramePlayer;
import com.netease.study.ui.surface.base.FrameRenderer;
import com.netease.study.ui.surface.base.FrameSink;
import com.netease.study.ui.surface.base.InterVideoView;
import com.netease.study.ui.surface.base.PlayExecutor;

public class InfiniteFramePlayer extends FramePlayer {
    public InfiniteFramePlayer(InterVideoView videoView, FrameRenderer renderer, PlayExecutor executor) {
        super(videoView, renderer, executor);
    }

    public final FrameSink getFrameSink() {
        return new FrameSink() {
            @Override
            public void onFrame(Frame frame) {
                if (!acceptFrame()) {
                    return;
                }

                produce(frame, false);
            }
        };
    }

    @Override
    protected final boolean mayProduce() {
        return true;
    }

    private boolean acceptFrame() {
        return getState() != State.STOPPED;
    }
}