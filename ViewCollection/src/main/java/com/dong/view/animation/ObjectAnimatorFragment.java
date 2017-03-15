package com.dong.view.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

import com.dong.lib.utils.ToastUtil;
import com.dong.view.BaseFragment;
import com.dong.viewcollection.R;

/**
 * Created by 杜营 on 2016/6/13.
 *
 */
public class ObjectAnimatorFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView mImageView = new ImageView(mContext);
        mImageView.setBackgroundResource(R.mipmap.pagecontent_3);

        //第一个动画
        mImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        ObjectAnimator.ofFloat(mImageView, "rotationX", 0, 360).setDuration(1000).start();//旋转动画1
        viewAnimatorSet1(mImageView);//动画集合1，延迟1s执行
        viewAnimatorSet2(mImageView);//动画集合2，延迟5s执行
        return mImageView;
    }

    private void viewAnimatorSet1(final View view){
        //此属性值并不存在，我们只是用这个方法获取变化值，然后手动改变View的属性
        ObjectAnimator animatorSet = ObjectAnimator.ofFloat(view,"doing",1.0f,0.3f).setDuration(1000);
        animatorSet.setStartDelay(1000);
        animatorSet.setRepeatCount(3);
        animatorSet.setRepeatMode(ObjectAnimator.REVERSE);
        animatorSet.start();

        animatorSet.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float values = (float) animation.getAnimatedValue();
                view.setAlpha(values);
                view.setScaleX(values);
                view.setScaleY(values);
            }
        });

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ToastUtil.show("动画1执行完毕，等待动画2执行");
            }
        });
    }

    private void viewAnimatorSet2(View view){
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.1f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.6f);

        ObjectAnimator animatorSet = ObjectAnimator.ofPropertyValuesHolder(view, alpha, scaleX, scaleY).setDuration(1000);
        animatorSet.setStartDelay(5000);
        animatorSet.setRepeatCount(3);
        animatorSet.setRepeatMode(ObjectAnimator.REVERSE);
        animatorSet.start();
    }
}
