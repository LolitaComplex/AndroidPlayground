package com.doing.architectureencapsulation.receiver;

import android.content.Context;
import android.util.Log;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-14.
 */

public class MiPushMessageReceiver extends PushMessageReceiver{
    private static final String TAG = "MiPushMessageReceiver";
    private String mRegId;

    //全部是运行在子线程，非UI线程

    /**
     * 透传消息的回调，直接把消息给我们，仅此而已
     * @param context
     * @param miPushMessage
     */
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceivePassThroughMessage(context, miPushMessage);
        Log.d(TAG, "onReceivePassThroughMessage: " + miPushMessage.getContent());
    }

    /**
     * 通知消息。会弹出一个通知
     * @param context
     * @param miPushMessage
     */
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageClicked(context, miPushMessage);
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageArrived(context, miPushMessage);
    }

    @Override
    public void onReceiveMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceiveMessage(context, miPushMessage);
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onReceiveRegisterResult(context, miPushCommandMessage);
    }

    /**
     * 我的API与小米服务器交互时，会回调.
     * @param context
     * @param miPushCommandMessage
     */
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onCommandResult(context, miPushCommandMessage);
        String command = miPushCommandMessage.getCommand();
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                //如果REGISTER返回为成功时会拿到一个REGISTER的ID，用来唯一标识一个用户，如果要做比较完善的推送，需要把此ID上报给服务器
                //通过此Id来对推送进行分流，比如这个消息指定推送给哪些用户
                //这里暂时存储这个ID
                mRegId = miPushCommandMessage.getCommandArguments().get(0);
            }
        }
    }
}
