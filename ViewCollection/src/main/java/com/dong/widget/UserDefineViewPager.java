package com.dong.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.dong.lib.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 杜营 on 2016/6/6.
 *
 */
public class UserDefineViewPager extends ViewPager {

    private Map<Integer,View> cacheViewFactory = new HashMap<>();

    public UserDefineViewPager(Context context) {
        super(context);
    }

    public UserDefineViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        View left = findObjectForPostion(position);
        View right = findObjectForPostion(position+1);
//        View left = getChildAt(position);
//        View right = getChildAt(position+1);

        animateStack(left, right, offset, offsetPixels);

        super.onPageScrolled(position, offset, offsetPixels);
    }

    private void animateStack(View left, View right, float offset, int offsetPixels) {
        if(right != null){
            float scale = 0.5f * offset + 0.5f;
            float trans = -getWidth()-getPageMargin()+offsetPixels;
            LogUtils.e(getClass().getName(),"偏移量变化为" + offsetPixels +"getWidth()"+getWidth()+"getPagemargin()"+getPageMargin()+"trans()"+trans );
            right.setScaleX(scale);
            right.setScaleY(scale);
            //right.setRotation(scale);
            right.setTranslationX(trans);
        }

        if(left != null){
            float scale = 0.5f*(1-offset)+0.5f;

            left.setScaleX(scale);
            left.setScaleY(scale);
            left.bringToFront();
        }
    }

    public void setObjectForPosition(int position,View view){
        cacheViewFactory.put(position, view);
    }

    public View findObjectForPostion(int position){
        return cacheViewFactory.get(position);
    }


}
