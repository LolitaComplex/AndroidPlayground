package com.dong.menum;

import java.io.Serializable;

/**
 * Created by 杜营 on 2016/6/6.
 *
 */
public enum AnimationViewType implements Serializable,ViewConllectionType {

    DrawableAnimtion,
    ViewAnimationInCode,
    ViewAnimationInXml,
    ObjectAnimator,
    ValueAnimator,
    AnimatorSet,
    ObjectAnimatorInXml,
    LayoutTransition,
    ViewAnimator;

    private String className;
    AnimationViewType(){
        className = "com.dong.view.animation." + name() + "Fragment";
    }

    public String getTypeClassName(){
        return className;
    }
}
