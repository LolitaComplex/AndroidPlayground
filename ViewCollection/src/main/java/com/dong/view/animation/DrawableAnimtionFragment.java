package com.dong.view.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.dong.view.BaseFragment;
import com.dong.viewcollection.R;

/**
 * Created by 杜营 on 2016/6/12.
 *
 */
public class DrawableAnimtionFragment extends BaseFragment {

    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        imageView = new ImageView(mContext);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        imageView.setBackgroundResource(R.drawable.frame_animation);
        AnimationDrawable animation = (AnimationDrawable) imageView.getBackground();
        animation.start();
        return imageView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        imageView.clearAnimation();
    }
}
