package com.doing.canvas.surface.base;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-02-07.
 */

public interface Executor {

    void setRunner(Runnable runner);

    void runNow();

    void runDelay(long delay);

    void drain();
}
