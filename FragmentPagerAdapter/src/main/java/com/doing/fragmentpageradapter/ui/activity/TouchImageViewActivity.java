package com.doing.fragmentpageradapter.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.doing.fragmentpageradapter.R;

import java.util.Arrays;
import java.util.List;

public class TouchImageViewActivity extends AppCompatActivity {

    private static final String TAG = "TouchImageViewActivity";

    private ViewPager mVpPager;
    private List<Integer> mData;

    public static void start(Context context) {
        context.startActivity(new Intent(context, TouchImageViewActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_image_view);

        mVpPager = ((ViewPager) findViewById(R.id.TouchImageVIewActivity_vp_pager));
        initVariable();
        initView();
    }

    private void initVariable() {
        mData = Arrays.asList(R.drawable.acg_wallpager01, R.drawable.acg_wallpager02,
                R.drawable.acg_wallpager03);
    }

    private void initView() {
        mVpPager.setAdapter(new MyPagerAdapter(mData));
    }

    class MyPagerAdapter extends PagerAdapter {

        private final LayoutInflater mInflater;
        private final List<Integer> data;

        MyPagerAdapter(List<Integer> data) {
            mInflater = LayoutInflater.from(TouchImageViewActivity.this);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View inflate = mInflater.inflate(R.layout.item_touch_image_view,
                    container, false);
            final ImageView ivContent = (ImageView) inflate.findViewById(
                    R.id.TouchImageVIewActivity_Item_siv);

            ivContent.setImageResource(data.get(position));
            container.addView(inflate);
            return inflate;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }



}
