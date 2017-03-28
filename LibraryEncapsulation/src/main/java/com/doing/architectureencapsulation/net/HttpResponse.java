package com.doing.architectureencapsulation.net;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-16.
 */

public class HttpResponse<T> {

    public Integer code;
    public String message;
    public T data;
}
