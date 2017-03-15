package com.dong.viewcollection;

import com.dong.view.BaseFragment;

/**
 * Created by 杜营 on 2016/6/12.
 *
 */
public interface ActivityCallback {

    /**
     * 监听当前显示的Fragment
     * @param fragment
     */
    void setSelectedFragment(BaseFragment fragment);


    /**
     * 设计的严重漏洞，解决Fragment中ActionBar的回调问题
     * @param fragment
     * @return
     */
    void setSpecificFragment(BaseFragment fragment);
}
