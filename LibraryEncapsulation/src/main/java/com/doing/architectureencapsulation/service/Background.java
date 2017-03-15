package com.doing.architectureencapsulation.service;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.doing.architectureencapsulation.constants.ExerciseConst;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-14.
 */

public final class Background {

    private static class InstanceHolder {
        final static Background INSTANCE = new Background();
    }

    public static Background getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void poll(@NonNull Context context) {
        Intent intent = new Intent(context, PollService.class);
        context.startService(intent);
    }

    public void startMiPush(@NonNull Context context) {
        MiPushClient.registerPush(context, ExerciseConst.MI_PUSH_KEY, ExerciseConst.MI_PUSH_SECRET);
    }

    private Background(){}
}
