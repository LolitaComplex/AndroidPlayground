package com.doing.canvas.surface.player;

import android.view.SurfaceHolder;

import com.doing.canvas.surface.base.Executor;
import com.doing.canvas.surface.base.Frame;
import com.doing.canvas.surface.base.Renderer;
import com.doing.canvas.surface.frame.FiniteFrameGenerator;


/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-02-07.
 */

public class FiniteFramePlayer extends FramePlayer {


    private final FiniteFrameGenerator mFiniteFrameGenerator;

    private boolean loopback;

    private boolean backward;

    public FiniteFramePlayer(Executor executor, Renderer renderer, SurfaceHolder holder, int frames) {
        super(executor, renderer, holder);

        mFiniteFrameGenerator = new FiniteFrameGenerator(frames);
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

        Frame frame = mFiniteFrameGenerator.next(backward, loopback);
        if (frame != null) {
            produce(frame);
        }
    }

    @Override
    protected void onState(State state) {
        if (state == State.STOPPED) {
            mFiniteFrameGenerator.stop();
        }
    }

    @Override
    protected Frame produce() {
        return isAuto() ? mFiniteFrameGenerator.next(backward, loopback) : null;
    }

    private boolean isAuto() {
        return getState() == State.PLAYING;
    }

    @Override
    protected boolean mayProduce() {
        return isAuto();
    }
}
