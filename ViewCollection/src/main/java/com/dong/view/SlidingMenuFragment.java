package com.dong.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dong.adapter.SimpleAdapter;
import com.dong.lib.utils.ToastUtil;
import com.dong.viewcollection.R;

import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by 杜营 on 2016/8/25.
 *
 */
public class SlidingMenuFragment extends BaseFragment {

    @BindView(R.id.SlidingMenuFragment_recycler)
    protected RecyclerView mRecyclerView;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sdlidingmenu, container, false);
    }

    @Override
    protected void loadData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        SimpleAdapter simpleAdapter = new SimpleAdapter(Arrays.asList("条目1", "条目2", "条目3", "条目4"));
        mRecyclerView.setAdapter(simpleAdapter);

        simpleAdapter.setOnSimpleItemClickListener(new SimpleAdapter.OnSimpleItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.show("点击了条目" + position);
            }
        });
    }

}
