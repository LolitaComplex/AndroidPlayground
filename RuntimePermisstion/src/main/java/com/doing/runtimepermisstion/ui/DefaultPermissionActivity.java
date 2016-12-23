package com.doing.runtimepermisstion.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.doing.runtimepermisstion.R;
import com.doing.runtimepermisstion.utils.RuntimePermissionUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import rx.functions.Action0;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class DefaultPermissionActivity extends AppCompatActivity {

    private static final String TAG = "DefaultPermission";

    public static void start(Context context) {
        context.startActivity(new Intent(context, DefaultPermissionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_permission);

        findViewById(R.id.DefaultPermisstionActivity_dail)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RuntimePermissionUtils.requestPermisstion(
                                DefaultPermissionActivity.this,
                                Manifest.permission.CALL_PHONE,
                                "如果您不允许该权限将无法拨打电话",
                                new Action0() {
                                    @Override
                                    public void call() {
                                        callPhone();
                                    }
                                });
                    }
                });
    }

    private void callPhone() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:13581503173"));
            startActivity(intent);
            Toast.makeText(this, "开始打电话", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "call: Exception", e);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        RuntimePermissionUtils.getResult(requestCode, grantResults, new Action0() {
            @Override
            public void call() {
                callPhone();
            }
        }, new Action0() {
            @Override
            public void call() {
                Toast.makeText(DefaultPermissionActivity.this, "您拒绝了权限", Toast.LENGTH_SHORT).show();
            }
        });
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
