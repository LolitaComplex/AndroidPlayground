package com.netease.study.ui.surface.base;

public interface PlayExecutor {
    void setRunner(Runnable runner);

    void requestNow();

    void requestDelayed(int delay);

    void drain();
}
