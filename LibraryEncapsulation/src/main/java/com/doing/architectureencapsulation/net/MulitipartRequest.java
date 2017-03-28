package com.doing.architectureencapsulation.net;

import com.doing.architectureencapsulation.net.BaseRequest;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-16.
 */

public abstract class MulitipartRequest extends BaseRequest {

    @Override
    protected RequestBody getResquestBody() {
        return new MultipartBody.Builder().addPart(
                new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return null;
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {

                    }
                }
        ).build();
    }

    @Override
    public String getRequestString() {
        return null;
    }

    @Override
    public MediaType getMediaType() {
        return null;
    }
}
