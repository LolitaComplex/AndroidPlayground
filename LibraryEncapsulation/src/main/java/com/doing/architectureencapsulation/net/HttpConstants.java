package com.doing.architectureencapsulation.net;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-16.
 */

public class HttpConstants {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({POST, GET, DELETE, PUT})
    public @interface HttpMethodMode {}

    public static final int POST = 0;
    public static final int GET= 1;
    public static final int DELETE = 2;
    public static final int PUT = 3;

    public static final String TYPE_JSON = "application/json; charset=utf-8";
    public static final String TYPE_FORM = "application/x-www-form-urlencoded; charset=utf-8";


    public static final int SUCCESS = 1;
    public static final int FAILD = 2;
    public static final int ERROR = -1;
    public static final int NET_ERROR = -200;
    public static final int JSON_ERROR = -201;
}
