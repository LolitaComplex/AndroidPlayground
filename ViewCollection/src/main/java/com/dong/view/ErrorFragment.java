package com.dong.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dong.tool.ShowToast;

/**
 * Created by Dy on 2016/2/25.
 *
 */
public class ErrorFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(mContext);
        textView.setText("类型没有对应好，出现错误");
        textView.setTextColor(Color.RED);
        textView.setTextSize(20.0f);
        ShowToast.showText(mContext, "到达了" + this.getClass().getName(), Toast.LENGTH_SHORT);
        return textView;
    }
}
