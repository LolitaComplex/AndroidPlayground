package com.dong.view;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dong.lib.utils.UIUtils;
import com.dong.viewcollection.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Doing on 2016/11/7.
 *
 */
public class BottomSheetFragment extends BaseFragment {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = UIUtils.inflate(R.layout.fragment_bottom_sheet, container);

        View bottomView = inflate.findViewById(R.id.bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomView);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                Toast.makeText(mContext, "onStateChanged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
                Toast.makeText(mContext, "onSlide", Toast.LENGTH_SHORT).show();
            }
        });

        final BottomSheetDialog dialog = new BottomSheetDialog(mContext);
        ListView listView = new ListView(mContext);
        List<String> data = Arrays.asList("蕾米莉亚", "芙兰朵露", "帕秋莉", "咲夜", "红美玲", "露米娅", "博丽灵梦", "雾雨魔理沙");
        listView.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, data));
        dialog.setContentView(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
            }
        });

        inflate.findViewById(R.id.btn_instuction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


        return inflate;
    }
}
