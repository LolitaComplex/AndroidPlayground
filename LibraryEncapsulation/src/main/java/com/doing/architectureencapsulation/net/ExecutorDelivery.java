package com.doing.architectureencapsulation.net;

import android.os.Handler;

import java.util.concurrent.Executor;

import static com.xiaomi.push.thrift.a.U;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-16.
 */

public class ExecutorDelivery {

    private final Executor mResponsePoster;

    public ExecutorDelivery(final Handler handler) {
        mResponsePoster = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    public ExecutorDelivery(Executor responsePoster) {
        mResponsePoster = responsePoster;
    }

    public void postSuccessResponse(HttpCallback httpCallback, BaseRequest request, HttpResponse response) {
        mResponsePoster.execute(ResponseDeliveryRunnable.successResponse(httpCallback, request, response));
    }

    public void postFailedResponse(HttpCallback httpCallback, BaseRequest request, Exception e) {
        mResponsePoster.execute(ResponseDeliveryRunnable.failedResponse(httpCallback, request, e));
    }

    private static class ResponseDeliveryRunnable<T> implements Runnable{

        public static <T> ResponseDeliveryRunnable<T> successResponse(HttpCallback<T> modelCallback,
                                                               BaseRequest request, HttpResponse<T> response) {
            return new ResponseDeliveryRunnable<>(modelCallback, request, response);
        }

        public static <T> ResponseDeliveryRunnable<T> failedResponse(HttpCallback<T> modelCallback, BaseRequest request, Exception e) {
            return new ResponseDeliveryRunnable<>(modelCallback, request, e);
        }

        private HttpCallback<T> callback;
        private BaseRequest request;
        private HttpResponse<T> httpResponse;
        private Exception exception;


        public ResponseDeliveryRunnable(HttpCallback<T> callback, BaseRequest request, HttpResponse<T> httpResponse) {
            this.callback = callback;
            this.request = request;
            this.httpResponse = httpResponse;
        }

        public ResponseDeliveryRunnable(HttpCallback<T> callback, BaseRequest request, Exception exception) {
            this.callback = callback;
            this.request = request;
            this.exception = exception;
        }

        @Override
        public void run() {
            if (httpResponse != null) {
                callback.onSuccess(request, httpResponse.data);
            }

            if (exception != null) {
                callback.onFailure(request, exception, HttpConstants.NET_ERROR, "网络错误");
            }
        }
    }

}
