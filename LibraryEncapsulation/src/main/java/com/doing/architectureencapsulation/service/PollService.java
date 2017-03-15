package com.doing.architectureencapsulation.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-14.
 */

public class PollService extends IntentService {

    public interface OnPollResultListener{
        void onPollResult(@Nullable String result);
    }

    private static Set<OnPollResultListener> sLintener = new HashSet<>();

    public static void addListener(@NonNull final OnPollResultListener listener) {
        if (!ThreadUtils.isMainThread()) {
            throw new RuntimeException("this method can only be called on main thread");
        }
        sLintener.add(listener);
    }

    public static void removeListener(@NonNull final OnPollResultListener listener) {
        if (!ThreadUtils.isMainThread()) {
            throw new RuntimeException("this method can only be called on mina thread");
        }
        sLintener.remove(listener);
    }

    public PollService() {
        super("PollService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        notifyResult("开始轮训");
    }

    private void notifyResult(final String result) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                //备份一份的原因是防止一部分人能够remove我们注册的Listener
                Set<OnPollResultListener> listeners = new HashSet<>();
                listeners.addAll(sLintener);

                for (OnPollResultListener listener : listeners) {
                    listener.onPollResult(result);
                }
                listeners.clear();
            }
        });
    }
}
