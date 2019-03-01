package com.netease.study.ui.surface.finite;

import com.netease.study.ui.surface.base.Frame;
import com.netease.study.ui.surface.base.FramePlayer;
import com.netease.study.ui.surface.base.FrameRenderer;
import com.netease.study.ui.surface.base.InterVideoView;
import com.netease.study.ui.surface.base.PlayExecutor;

public class FiniteFramePlayer extends FramePlayer {
    // play forward or backward, only valid when State.PLAYING
    private boolean backward;

    // loopback mode
    private boolean loopback;

    // number generator
    private final FiniteFrameGenerator generator;

    public FiniteFramePlayer(InterVideoView videoView, FrameRenderer renderer, PlayExecutor executor, int frames) {
        super(videoView, renderer, executor);

        generator = new FiniteFrameGenerator(frames);
    }

    public void setLoopback(boolean loopback) {
        this.loopback = loopback;
    }

    public final void play(boolean backward) {
        if (play()) {
            this.backward = backward;
        }
    }

    public final void step(boolean backward) {
        pause();

        Frame frame = generator.next(backward, loopback);
        if (frame != null) {
            produce(frame);
        }
    }

    @Override
    protected void onState(State state) {
        if (state == State.STOPPED) {
            generator.stop();
        }
    }

    @Override
    protected Frame produce() {
        return isAuto() ? generator.next(backward, loopback) : null;
    }

    @Override
    protected boolean mayProduce() {
        return isAuto();
    }

    private boolean isAuto() {
        return getState() == State.PLAYING;
    }
}
