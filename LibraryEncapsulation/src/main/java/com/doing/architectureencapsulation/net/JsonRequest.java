package com.doing.architectureencapsulation.net;

import com.google.gson.GsonBuilder;

import okhttp3.MediaType;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-16.
 */

public abstract class JsonRequest<T> extends BaseRequest<T> {

    public static final MediaType MEDIA_JSON =
            MediaType.parse(HttpConstants.TYPE_JSON);

    @Override
    public String getRequestString() {
        try {
            if (mBodyMap != null && mBodyMap.size() > 0) {
                    return new GsonBuilder().enableComplexMapKeySerialization()
                        .create().toJson(mBodyMap);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    @Override
    public MediaType getMediaType() {
        return MEDIA_JSON;
    }

}
