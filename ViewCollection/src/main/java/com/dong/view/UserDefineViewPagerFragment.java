package com.dong.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.dong.lib.utils.UIUtils;
import com.dong.widget.UserDefineViewPager;

/**
 * Created by 杜营 on 2016/6/6.
 *
 */
public class UserDefineViewPagerFragment extends BaseFragment {

    private UserDefineViewPager mPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPager = new UserDefineViewPager(mContext);
        mPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        initListener();
        return mPager;
    }

    private void initListener() {
        mPager.setAdapter(new MyPageAdapter());
    }

    private class MyPageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 9;
    }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(UIUtils.getContext());
            int id;
            if(position <= 2){
                id = UIUtils.getIdentifier("pagecontent_" + (position+1), "mipmap");
            }else{
                id = UIUtils.getIdentifier("happy_new_year_" + (position-2),"mipmap");
            }
            imageView.setImageResource(id);
            container.addView(imageView);
            mPager.setObjectForPosition(position,imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}