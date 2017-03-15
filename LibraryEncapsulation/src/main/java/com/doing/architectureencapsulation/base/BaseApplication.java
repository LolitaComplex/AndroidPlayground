package com.doing.architectureencapsulation.base;

import android.app.Application;
import android.support.annotation.Nullable;
import android.util.Log;

import com.doing.architectureencapsulation.service.Background;
import com.doing.architectureencapsulation.service.PollService;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-14.
 */

public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";
    private static BaseApplication instance;

    private PollService.OnPollResultListener mListener;

    public BaseApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Background.getInstance().poll(this);
    }

    private PollService.OnPollResultListener getListener(){
        if (mListener != null) {
            mListener = new PollService.OnPollResultListener() {
                @Override
                public void onPollResult(@Nullable String result) {
                    Log.d(TAG, "onPollResult: " + result);

                }
            };
        }
        return mListener;
    }
}
