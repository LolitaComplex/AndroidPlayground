package com.dong.view.design;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dong.lib.utils.ToastUtil;
import com.dong.view.BaseFragment;
import com.dong.viewcollection.R;


/**
 * Created by 杜营 on 2016/7/27.
 *
 */
public class CoordinatorToolBarFragment extends BaseFragment {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext.setTheme(R.style.AppThemeNoActionBar);
        View inflate = inflater.inflate(R.layout.fargment_coordirator_toolbar, container, false);

        Toolbar toolBar = (Toolbar) inflate.findViewById(R.id.ToolBarFragment_toolbar);
        toolBar.setTitle("这是真Tittle");
        toolBar.setSubtitle("这是真副标题");

        mContext.setSupportActionBar(toolBar);

        toolBar.inflateMenu(R.menu.menu_design_coordinator);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_search:
                        ToastUtil.show("照相");
                        break;
                }
                return true;
            }
        });
        return inflate;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.ToolBarFragment_text);
        String text = textView.getText().toString();
        StringBuilder sb = new StringBuilder();
        for(int x=0;x<100;x++) {
            sb.append(text);
        }
        textView.setText(sb.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.setTheme(R.style.AppTheme);
    }
}
