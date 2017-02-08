package main.java.com.doing.canvas.surface.player;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import main.java.com.doing.canvas.surface.base.Executor;
import main.java.com.doing.canvas.surface.base.Frame;
import main.java.com.doing.canvas.surface.base.Renderer;
import main.java.com.doing.canvas.surface.frame.FrameManager;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-02-07.
 */

public abstract class FramePlayer {

    private static final String TAG = "FramePlayer";

    private static final int DEFAULT_INTERVAL = 40;//默认间隔

    public enum State {
        PLAYING,
        STOPPED,
        PAUSED;
    }

    public interface Callback{
        void onState(State state);
    }

    private State state = State.STOPPED;

    private int interval = DEFAULT_INTERVAL;

    private Callback mCallback;

    private final Executor mExecutor;
    private final Renderer mRenderer;
    private final FrameManager mFrameFactory = new FrameManager();
    private final SurfaceHolder mSurfaceHolder;

    public FramePlayer(Executor executor, Renderer renderer, SurfaceHolder holder) {
        mExecutor = executor;
        mRenderer = renderer;
        mSurfaceHolder = holder;

        executor.setRunner(new Runnable() {
            @Override
            public void run() {
                renderFrame();
            }
        });

        initSurfaceHolder();
    }

    private void initSurfaceHolder() {
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, "surfaceCreated: ");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "surfaceChanged: ");

                stop();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, "surfaceDestroyed: ");

                stop();
            }
        });
    }

    public final void setFPS(int fps) {
        if (fps > 0) {
            interval = 1000 / fps;//每1s时间内帧数
        }
    }

    public final void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public final boolean play() {
        if (state == State.PLAYING) {
            return false;
        }
        changeState(State.PLAYING);
        mExecutor.runNow();
        return true;
    }

    public final boolean pause() {
        if (state != state.PLAYING) {
            return false;
        }
        changeState(State.PAUSED);
        mExecutor.drain();
        return true;
    }

    public final void stop() {
        if (state == State.STOPPED) {
            return;
        }
        changeState(State.STOPPED);
        mExecutor.drain();
        mFrameFactory.drain();
    }

    private void changeState(State state) {
        this.state = state;
        onState(state);
        if (mCallback != null) {
            mCallback.onState(state);
        }
    }

    private void renderFrame() {
        Frame frame = mFrameFactory.consume();
        if (frame == null) {
            frame = produce();
        }

        if (frame != null) {
            renderFrame(frame);
        }

        //TODO
        if (mayProduce() || !mFrameFactory.isEmpty()) {
            mExecutor.runDelay(interval);
        }
    }

    private void renderFrame(Frame frame) {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        if (canvas == null) {
            return;
        }

        mRenderer.render(canvas, frame);

        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    protected abstract Frame produce();


    protected void produce(Frame frame) {
        if (mFrameFactory.produce(frame)) {
            mExecutor.runNow();
        }
    }

    protected void onState(State state) {

    }

    public final State getState() {
        return state;
    }

    protected boolean mayProduce() {
        return false;
    }
}
