package com.dong.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Dy on 2016/5/20.
 *
 */
public class DialogFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(mContext);
        textView.setText("这是一个DialogFragment，已经替代了AlterDialog在日常中的使用");
        textView.setTextSize(20);
        textView.setTextColor(Color.rgb(60, 180, 79));
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog();
            }
        });

        return textView;
    }

    private void initDialog() {
        ShowDialogFragment.showDialog(getFragmentManager(),"FristDialog");
    }
}
