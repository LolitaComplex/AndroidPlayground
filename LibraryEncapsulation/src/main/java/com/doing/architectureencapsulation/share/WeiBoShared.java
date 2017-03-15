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

public class WeiBoShared extends AbstractSharedApi {

    public WeiBoShared(Activity context) {
        super(context);
    }

    @Override
    public void share(@Nullable String content, @Nullable String imagePath) {

    }
}
