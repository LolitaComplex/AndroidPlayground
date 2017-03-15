package com.dong.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dong.viewcollection.R;

/**
 * Created by Dy on 2016/2/25.
 *
 */
public class SpinnerFragment extends  BaseFragment {


    private Spinner spin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spinner,container,false);

        findViewById(view) ;
        initView();

        return view;
    }



    private void findViewById(View view) {
        spin = (Spinner) view.findViewById(R.id.spin);
    }

    private void initView() {
        spin.measure(0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spin.setDropDownVerticalOffset(spin.getMeasuredHeight());
        }
        String[] arr = {"博丽灵梦", "雾雨魔理沙", "红美玲", "帕秋莉诺蕾姬", "芙兰朵露斯卡雷特"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_multiple_choice, arr);

        spin.setAdapter(adapter);
    }
}
