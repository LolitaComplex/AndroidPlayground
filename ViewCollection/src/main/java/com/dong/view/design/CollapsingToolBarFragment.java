package com.dong.view.design;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dong.view.BaseFragment;
import com.dong.viewcollection.R;

/**
 * Created by 杜营 on 2016/7/27.
 *
 */
public class CollapsingToolBarFragment extends BaseFragment{

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collapsing_toolbar,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolBar = (Toolbar) view.findViewById(R.id.ToolBarFragment_toolbar);
        mContext.setSupportActionBar(toolBar);

        mContext.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = (TextView) view.findViewById(R.id.ToolBarFragment_text);
        String text = textView.getText().toString();
        StringBuilder sb = new StringBuilder();
        for(int x=0;x<100;x++) {
            sb.append(text);
        }
        textView.setText(sb.toString());
    }

}
