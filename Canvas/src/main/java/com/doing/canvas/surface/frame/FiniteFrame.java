package com.doing.canvas.surface.frame;


import com.doing.canvas.surface.base.Frame;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-02-07.
 */

public class FiniteFrame implements Frame {

    private int number;

    public FiniteFrame(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
