package com.dong.view.commonadapter.comrecycleradapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Doing on 2016/9/13.
 *
 */
public abstract class RecyclerMultiItemAdapter<T> extends RecyclerBaseAdapter<T> {

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public RecyclerMultiItemAdapter(Context context, List<T> data, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, -1, data);
        this.mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position, mData.get(position));
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        RecyclerHolder holder = RecyclerHolder.get(mContext, parent, layoutId);
        return holder;
    }

}
