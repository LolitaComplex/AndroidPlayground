package com.doing.architectureencapsulation.net;

import okhttp3.MediaType;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-16.
 */

public abstract class FormRequest<T> extends BaseRequest<T> {

    public static final MediaType MEDIA_FORM = MediaType.parse(HttpConstants.TYPE_FORM);

    @Override
    public MediaType getMediaType() {
        return MEDIA_FORM;
    }

    @Override
    public String getRequestString() {
        //key=value&key2=value2
        if (mBodyMap != null && mBodyMap.size() > 0) {
            return encodeParameters(mBodyMap, "utf-8");
        }
        return null;
    }
}
