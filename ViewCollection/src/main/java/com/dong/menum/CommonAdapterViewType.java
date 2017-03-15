package com.dong.menum;

import java.io.Serializable;

/**
 * Created by Doing on 2016/9/9.
 *
 *
 * 鸿洋4篇
 * http://blog.csdn.net/lmj623565791/article/details/45059587
 * http://blog.csdn.net/lmj623565791/article/details/51118836
 * http://blog.csdn.net/lmj623565791/article/details/38902805/
 * http://blog.csdn.net/lmj623565791/article/details/51854533
 *
 * Piasy
 * http://blog.piasy.com/2016/03/26/Insight-Android-RecyclerView-ItemDecoration/
 * http://blog.piasy.com/2016/04/04/Insight-Android-RecyclerView-ItemAnimator/
 *
 * 天之界线
 * https://www.zybuluo.com/shark0017/note/202443
 *
 */
public enum  CommonAdapterViewType implements Serializable,ViewConllectionType {
    ListViewAdapter,RecyclerViewAdapter,MultiItemRecyclerViewAdapter;

    private String mClassName;

    CommonAdapterViewType(){
        this.mClassName = "com.dong.view.commonadapter." + name() + "Fragment";
    }

    @Override
    public String getTypeClassName() {
        return mClassName;
    }
}
