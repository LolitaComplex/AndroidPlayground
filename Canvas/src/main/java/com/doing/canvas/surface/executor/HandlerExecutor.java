package com.doing.canvas.surface.executor;

import android.os.Handler;

import com.doing.canvas.surface.base.Executor;


/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-02-07.
 */

public class HandlerExecutor implements Executor {


    private final Handler handler;
    private Runnable runner;

    public HandlerExecutor(){
        this(null);
    }

    public HandlerExecutor(Handler handler) {
        this.handler = handler == null ? new Handler() : handler;
    }

    @Override
    public void setRunner(Runnable runner) {
        this.runner = runner;
    }

    @Override
    public void runNow() {
        handler.post(runner);
    }

    @Override
    public void runDelay(long delay) {
        handler.postDelayed(runner, delay);
    }

    @Override
    public void drain() {
        handler.removeCallbacks(runner);
    }
}
