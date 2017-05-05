package com.doing.architectureencapsulation.net.practive;

import okhttp3.MediaType;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-04-24.
 */

public abstract class FormRequest<T> extends BaseRequest<T> {

    @Override
    public MediaType getMediaType() {
        return MediaType.parse("application/x-www-urlencoded; charset=utf-8");
    }

    @Override
    public String getRequestSting() {
        if (mBodyMap != null && mBodyMap.size() > 0) {
            return encodeParameters(mBodyMap, "utf-8");
        }
        return null;
    }
}
