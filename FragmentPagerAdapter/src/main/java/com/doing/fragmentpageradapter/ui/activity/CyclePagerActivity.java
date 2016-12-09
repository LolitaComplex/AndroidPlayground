package com.doing.fragmentpageradapter.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.doing.fragmentpageradapter.R;
import com.doing.fragmentpageradapter.ui.fragment.BaseFragment;

/**
 * Created by Doing on 2016/12/9.
 *
 */
public class CyclePagerActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, CyclePagerActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_pager);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
//        pager.setAdapter();

    }

    private class MyPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }
}
