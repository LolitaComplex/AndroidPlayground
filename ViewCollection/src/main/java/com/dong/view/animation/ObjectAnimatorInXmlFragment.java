package com.dong.view.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
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
 * Created by 杜营 on 2016/6/14.
 *
 */
public class ObjectAnimatorInXmlFragment extends BaseFragment {

    private ImageView mImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mImageView = new ImageView(mContext);
        mImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        mImageView.setImageResource(R.mipmap.pagecontent_3);

        startAnimatorInXml();
        return mImageView;
    }

    private void startAnimatorInXml() {
        Animator animator = AnimatorInflater.loadAnimator(mContext, R.animator.property_animator);
        mImageView.setPivotX(0);
        mImageView.setPivotY(0);
        mImageView.invalidate();
        animator.setTarget(mImageView);
        animator.start();
    }
}
