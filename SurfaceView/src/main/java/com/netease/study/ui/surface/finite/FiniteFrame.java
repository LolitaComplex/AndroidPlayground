package com.netease.study.ui.surface.finite;

import com.netease.study.ui.surface.base.Frame;

public class FiniteFrame implements Frame {
    private final int number;

    public FiniteFrame(int number) {
        this.number = number;
    }

    public final int getNumber() {
        return number;
    }
}
