package com.doing.architectureencapsulation.listadapter.recycler;

import android.support.annotation.LayoutRes;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-30.
 */

public interface ItemViewTaker<T> {

    @LayoutRes
    int getLayoutId();

    boolean getItemViewType(T data, int position);

    void convert(BaseViewHolder holder, T data, int position);

}
