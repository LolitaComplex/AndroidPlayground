package com.dong.widget;

import android.content.Context;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;

/**
 * Created by 杜营 on 2016/8/26.
 *
 */
public class DrawerArrowDrawableToggle extends DrawerArrowDrawable {
    /**
     * @param context used to get the configuration for the drawable from
     */
    public DrawerArrowDrawableToggle(Context context) {
        super(context);
    }

    public void setPosition(float position) {
        if (position == 1f) {
            setVerticalMirror(true);
        }else if (position == 0f) {
            setVerticalMirror(false);
        }

        setProgress(position);
    }

    public float getPosition() {
        return getProgress();
    }
}
