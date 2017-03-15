package com.dong.view.commonadapter.comrecycleradapter;

/**
 * Created by Doing on 2016/9/13.
 *
 */
public interface MultiItemTypeSupport<T> {

    int getLayoutId(int itemType);

    int getItemViewType(int position, T t);
}
