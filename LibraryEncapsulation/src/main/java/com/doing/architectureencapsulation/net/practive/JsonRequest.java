package com.doing.architectureencapsulation.net.practive;

import com.google.gson.GsonBuilder;

import okhttp3.MediaType;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-04-24.
 */

public abstract class JsonRequest<T> extends BaseRequest<T> {

    @Override
    public String getRequestSting() {
        if (mBodyMap != null && mBodyMap.size() > 0) {
            return new GsonBuilder().enableComplexMapKeySerialization()
                    .create().toJson(mBodyMap);
        }

        return null;
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.parse("application/json; charset=utf-8");
    }
}
