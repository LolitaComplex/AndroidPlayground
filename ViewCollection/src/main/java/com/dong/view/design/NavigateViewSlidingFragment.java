package com.dong.view.design;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dong.adapter.DividerItemDecoration;
import com.dong.adapter.SimpleAdapter;
import com.dong.lib.utils.ToastUtil;
import com.dong.view.BaseFragment;
import com.dong.viewcollection.R;

import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by 杜营 on 2016/8/29.
 *
 * 算了，不用试了，SlidingPaneLayout官方推荐作为内容导航，而NavigatiionView是应用导航菜单，宜使用DrawerLayout
 *
 */
public class NavigateViewSlidingFragment extends BaseFragment {

    @BindView(R.id.NavigateViewSlidingFragment_toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.NavigateViewSlidingFragment_navigate)
    protected NavigationView mNaviagationView;

    @BindView(R.id.NavigateViewSlidingFragment_recycl)
    protected RecyclerView mRecyclerView;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigatesliding, container, false);
    }

    @Override
    protected void loadData() {
        if (mToolbar != null) {
            mContext.setSupportActionBar(mToolbar);

            ActionBar actionBar = mContext.getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        final SimpleAdapter adapter = new SimpleAdapter(Arrays.asList(
                "我的世界", "仁王", "如龙", "黑暗之魂", "血源", "初音未来 未来之音", "第九日",
                "瑞奇与叮当", "神秘海域", "最后生还者", "北欧女神", "G线上的魔王", "在地下城邂逅一定是搞错了什么",
                "战神", "巫师3"));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL_LIST));

        adapter.setOnSimpleItemClickListener(new SimpleAdapter.OnSimpleItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.show(adapter.getData().get(position));
            }
        });
    }
}
