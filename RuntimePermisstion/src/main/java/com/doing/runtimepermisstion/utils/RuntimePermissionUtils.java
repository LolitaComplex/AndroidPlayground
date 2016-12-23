package com.doing.runtimepermisstion.utils;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;

public class RuntimePermissionUtils {

    private static final int PERMISSITION_CODE = 333;

    private static boolean checkPermisstion(Context context, String permisstion) {
        return ActivityCompat.checkSelfPermission(context, permisstion)
                != PackageManager.PERMISSION_GRANTED;
    }

    private static void requestPermissstion(Activity activity, String permisstion) {
        ActivityCompat.requestPermissions(activity, new String[]{permisstion}, PERMISSITION_CODE);
    }

    private static boolean dialogFlag(Activity activity, String permisstion) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permisstion);
    }

    private static Action1<Action2> getDefaultDialog(final Activity activity, final String permisstion
                                                     ,final String message) {

        return new Action1<Action2>() {
            @Override
            public void call(final Action2 action2) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(message);
                builder.setCancelable(false);
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        action2.call(activity, permisstion);
                    }
                });
                builder.show();
            }
        };
    }

    private static Action2<Activity, String> clickEnsure() {
        return new Action2<Activity, String>() {
            @Override
            public void call(Activity activity, String permisstion) {
                requestPermissstion(activity, permisstion);
            }
        };
    }

    public static void requestPermisstion(Activity activity, String permisstion, String message, Action0 callback) {
        if (checkPermisstion(activity, permisstion)) {
            boolean dialogFlag = dialogFlag(activity, permisstion);
            //是否显示授权原因，第一次为false。当用户点击了拒绝，这个Flag就是true了
            if (dialogFlag) {
                //在这个位置告知用户请求该权限的原因
                Action1<Action2> defaultDialog = getDefaultDialog(activity, permisstion, message);
                defaultDialog.call(clickEnsure());
            } else {
                requestPermissstion(activity, permisstion);
            }
        } else {
            callback.call();
        }
    }

    public static void getResult(int requestCode, int[] grantResults, Action0 callback, Action0 errorCallback) {
        switch (requestCode) {
            case PERMISSITION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callback.call();
                } else {
                    errorCallback.call();
                }
                break;
        }
    }

}
