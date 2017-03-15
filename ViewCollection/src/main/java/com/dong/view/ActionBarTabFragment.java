package com.dong.view;

import android.net.http.EventHandler;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dong.viewcollection.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dy on 2016/3/4.
 */
public class ActionBarTabFragment extends BaseFragment implements ActionBar.TabListener {

    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_actionbar_tab, container, false);

        findViewById(inflate);
        initView();

        return inflate;
    }


    private void findViewById(View root) {
        mViewPager = (ViewPager) root.findViewById(R.id.vp_tab);

    }

    private void initView() {
        FragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(mContext.getSupportFragmentManager());

        final ActionBar actionBar = mContext.getSupportActionBar();
        if (actionBar != null)
            actionBar.show();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (int x = 0; x < pagerAdapter.getCount(); x++) {
            actionBar.addTab(actionBar.newTab().setText(pagerAdapter.getPageTitle(x)).setTabListener(this));
        }

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onBackPressed() {
        ActionBar actionBar = mContext.getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
            return true;
        } else {
            return super.onBackPressed();
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> list;

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            list = new ArrayList<>();
            list.add(new ErrorFragment());
            list.add(new NotificationFragment());
            list.add(new SearchViewFragment());
            list.add(new SpinnerFragment());

        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        /**
         * 设置ViewPager的标题字符串
         *
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "错误";
                case 1:
                    return "通知页";
                case 2:
                    return "搜索页";
                default:
                    return "下拉弹窗";
            }
        }
    }

    @Override
    public void onDestroy() {
        final ActionBar actionBar = mContext.getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        super.onDestroy();
    }
}
