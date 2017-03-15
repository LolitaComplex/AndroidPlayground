package com.dong.menum;

import java.io.Serializable;

/**
 * Created by 杜营 on 2016/7/27.
 *
 */
public enum DesignViewType implements Serializable,ViewConllectionType{
    CoordinatorToolBar,
    CollapsingToolBar,
    NavigateViewDrawer,
    NavigateViewSliding;

    private String mClassName;

    DesignViewType(){
        mClassName = "com.dong.view.design." + name() + "Fragment";
    }

    @Override
    public String getTypeClassName() {
        return mClassName;
    }
}
