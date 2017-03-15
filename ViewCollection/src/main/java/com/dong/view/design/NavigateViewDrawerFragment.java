package com.dong.view.design;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
 * Created by 杜营 on 2016/8/26.
 *
 */
public class NavigateViewDrawerFragment extends BaseFragment {

    @BindView(R.id.NavigateViewDrawFragment_navigate)
    protected NavigationView mNavigationView;

    @BindView(R.id.NavigateViewDrawFragment_recycler)
    protected RecyclerView mRecycler;

    @BindView(R.id.NavigateViewDrawFragment_toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.NavigateViewDrawFragment_drawer)
    protected DrawerLayout mDrawerLayout;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigatedrawer, container, false);
    }

    @Override
    protected void loadData() {
        if (mToolbar != null) {
            mContext.setSupportActionBar(mToolbar);

            ActionBar actionBar = mContext.getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        mRecycler.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        final SimpleAdapter adapter = new SimpleAdapter(Arrays.asList(
                "我的世界", "仁王", "如龙", "黑暗之魂", "血源", "初音未来 未来之音", "第九日",
                "瑞奇与叮当", "神秘海域", "最后生还者", "北欧女神", "G线上的魔王", "在地下城邂逅一定是搞错了什么",
                "战神","巫师3"));
        mRecycler.setAdapter(adapter);
        mRecycler.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL_LIST));

        adapter.setOnSimpleItemClickListener(new SimpleAdapter.OnSimpleItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String text = adapter.getData().get(position);
                ToastUtil.show(text);
            }
        });

        //设置status bar的颜色
        TypedValue value = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.colorPrimaryDark, value, true);
        mDrawerLayout.setStatusBarBackgroundColor(value.data);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(mContext, mDrawerLayout,
                mToolbar, R.string.open_drawer, R.string.close_drawer);

        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);

        //配置NavigationView Menu的内容
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(handleNavigationItemSelected(item)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });

    }

    private boolean handleNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.drawer_home:
                ToastUtil.show("主页");
                break;
            case R.id.drawer_cart:
                ToastUtil.show("购物车");
                break;
            case R.id.drawer_setting:
                ToastUtil.show("设置");
                break;
            case R.id.drawer_collection:
                ToastUtil.show("收藏");
                break;
            case R.id.drawer_friend:
                ToastUtil.show("好友");
                break;
            case R.id.drawer_picture:
                ToastUtil.show("相册");
                break;
            default:
                return false;
        }
        mNavigationView.setCheckedItem(itemId);
        return true;
    }

    @Override
    public boolean onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }else{
            return super.onBackPressed();
        }
    }
}
