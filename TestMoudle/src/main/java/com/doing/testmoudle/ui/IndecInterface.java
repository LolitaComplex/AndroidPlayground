package com.doing.testmoudle.ui;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class IndecInterface {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({INDEC_MODE_PRIVATE, INDEC_MODE_PROTECTED, INDEC_MODE_DEFAULT, INDEC_MODE_PUBLIC})
    public @interface IndecMode {}

    public static final int INDEC_MODE_PRIVATE = 0;
    public static final int INDEC_MODE_PROTECTED = 1;
    public static final int INDEC_MODE_DEFAULT = 2;
    public static final int INDEC_MODE_PUBLIC = 3;

    @IndecMode
    public int getIndecMode() {
        return INDEC_MODE_DEFAULT;
    }


    public void setIndecMode(@IndecMode int mode) {

    }
}
