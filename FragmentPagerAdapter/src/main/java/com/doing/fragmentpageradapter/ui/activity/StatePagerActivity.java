package com.doing.fragmentpageradapter.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.doing.fragmentpageradapter.R;
import com.doing.fragmentpageradapter.ui.fragment.BaseFragment;

public class StatePagerActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, StatePagerActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        setTitle("FragmentStatePageAdapter");

        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(1);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        private static final String TAG = "StatePagerAdapter";
        private int mPosition = -1;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            mPosition = position;
            Log.e(TAG, "getItem() 第" + mPosition + "页");
            BaseFragment baseFragment = new BaseFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            baseFragment.setArguments(bundle);
            return baseFragment;
        }

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public void startUpdate(ViewGroup container) {
//            Log.e(TAG, "startUpdate() 第" + mPosition + "页");
            super.startUpdate(container);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.e(TAG, "instantiateItem() 第" + mPosition + "页");
            return super.instantiateItem(container, position);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            Log.e(TAG, "finishUpdate() 第" + mPosition + "页");
            super.finishUpdate(container);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
//            Log.e(TAG, "setPrimaryItem() 第" + mPosition + "页");
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Parcelable saveState() {
//            Log.e(TAG, "saveState() 第" + mPosition + "页");
            return super.saveState();
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
//            Log.e(TAG, "restoreState() 第" + mPosition + "页");
            super.restoreState(state, loader);
        }

        @Override
        public float getPageWidth(int position) {
            return 0.5f;
        }

    }

}
