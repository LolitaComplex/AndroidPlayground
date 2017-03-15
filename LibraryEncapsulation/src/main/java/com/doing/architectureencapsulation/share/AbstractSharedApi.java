package com.doing.architectureencapsulation.share;

import android.app.Activity;
import android.support.annotation.Nullable;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-15.
 */

public abstract class AbstractSharedApi implements ShareApi {

    protected Activity mContext;

    public AbstractSharedApi(Activity context) {
        this.mContext = context;
    }
}
