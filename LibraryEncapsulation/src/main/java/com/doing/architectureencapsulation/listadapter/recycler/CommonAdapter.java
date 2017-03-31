package com.doing.architectureencapsulation.listadapter.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;

import java.util.List;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-30.
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public CommonAdapter(final Context context, List<T> data, @LayoutRes final int layoutId) {
        super(context, data);
        mManager.addItemView(new ItemViewTaker<T>() {
            @Override
            public int getLayoutId() {
                return layoutId;
            }

            @Override
            public boolean getItemViewType(T data, int position) {
                return true;
            }

            @Override
            public void convert(BaseViewHolder holder, T data, int position) {
                CommonAdapter.this.convert(holder, data, position);
            }
        });
    }

    public abstract void convert(BaseViewHolder holder, T data, int position);
}
