package com.doing.architectureencapsulation.net;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-16.
 */

public interface HttpCallback<T> {

    void onSuccess(BaseRequest request, T data);

    void onFailure(BaseRequest request, Exception e, int code, String message);
}
