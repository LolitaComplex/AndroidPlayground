package com.dong.view.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.dong.view.BaseFragment;
import com.dong.viewcollection.R;

/**
 * Created by 杜营 on 2016/6/13.
 *
 */
public class ViewAnimationInXmlFragment extends BaseFragment {

    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        imageView = new ImageView(mContext);
        imageView.setLayoutParams(new LayoutParams(800, 1200));
        imageView.setScaleType(ScaleType.CENTER_CROP);
        imageView.setBackgroundResource(R.mipmap.happy_new_year_5);

        return imageView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Animation translateAnimation = AnimationUtils.loadAnimation(mContext, R.anim.translate_animate);
        imageView.startAnimation(translateAnimation);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        imageView.clearAnimation();
        imageView.setVisibility(View.GONE);
    }
}
