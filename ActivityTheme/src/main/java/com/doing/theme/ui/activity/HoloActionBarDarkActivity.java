package com.doing.theme.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.doing.theme.R;
import com.doing.theme.ui.base.BaseActivity;

public class HoloActionBarDarkActivity extends BaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, HoloActionBarDarkActivity.class));
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_holo_actionbar_dark;
    }
}
