package com.doing.theme.ui.base;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;

import com.doing.theme.R;
import com.doing.theme.utils.theme.GlobalTheme;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(GlobalTheme.getCurrentTheme());
        setContentView(initLayoutId());

        int[] attrs = {R.attr.bgcolor};
        TypedArray typedArray = getTheme().obtainStyledAttributes(attrs);
        int color = typedArray.getColor(0, Color.parseColor("#66ccff"));
        if (color != Color.parseColor("#66ccff")) {
            ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0).setBackgroundColor(color);
        }
    }

    protected abstract int initLayoutId();
}
