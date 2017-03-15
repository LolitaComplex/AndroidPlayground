package com.doing.architectureencapsulation.share;

import android.support.annotation.Nullable;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-15.
 */

public interface ShareApi {

    enum ShareType {
        SYSTEM,
        WEIBO,
        WECHAT
    }

    void share(@Nullable String content, @Nullable String imagePath);

}
