package com.dong.view.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.dong.view.BaseFragment;
import com.dong.viewcollection.R;

/**
 * Created by 杜营 on 2016/6/6.
 *
 */
public class ViewAnimationInCodeFragment extends BaseFragment {

    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        imageView = new ImageView(mContext);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        imageView.setBackgroundResource(R.mipmap.happy_new_year_1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return imageView;
    }

    @Override
    public void onResume() {
        super.onResume();
        startAnimation(imageView);
    }

    public void startAnimation(final View view){

        AnimationSet set = new AnimationSet(false);//是否使用插值器
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.5f);
        setAnimation(alphaAnimation);
        set.addAnimation(alphaAnimation);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,0.5f,1.0f,0.5f);
        setAnimation(scaleAnimation);
        scaleAnimation.setStartOffset(500);
        set.addAnimation(scaleAnimation);

        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0.1f,Animation.RELATIVE_TO_SELF,0.6f,
                Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0.7f);
        setAnimation(translateAnimation);
        translateAnimation.setStartOffset(1000);
        set.addAnimation(translateAnimation);

        RotateAnimation rotateAnimation = new RotateAnimation(0,180,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        setAnimation(rotateAnimation);
        rotateAnimation.setStartOffset(1500);
        set.addAnimation(rotateAnimation);

        view.startAnimation(set);
    }

    private void setAnimation(Animation animation){
        animation.setDuration(500);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setFillAfter(true);
        animation.setInterpolator(new AccelerateInterpolator());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        imageView.clearAnimation();
        imageView.setVisibility(View.GONE);
    }
}
