package com.doing.fragmentpageradapter.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.doing.fragmentpageradapter.R;
import com.doing.fragmentpageradapter.ui.fragment.BaseFragment;

public class PagerActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, PagerActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        setTitle("FragmentPagerAdapter");

        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment baseFragment = new BaseFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            baseFragment.setArguments(bundle);
            return baseFragment;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);

        }
    }
}
