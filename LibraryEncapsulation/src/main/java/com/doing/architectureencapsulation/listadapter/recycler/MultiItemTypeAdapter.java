package com.doing.architectureencapsulation.listadapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-30.
 */

public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>{

    protected final Context mContext;
    protected final List<T> mDatas;
    protected final ItemViewManager<T> mManager;

    public MultiItemTypeAdapter(Context context, List<T> data) {
        mContext = context;
        mDatas = data;
        mManager = new ItemViewManager<>();
    }

    @Override
    public int getItemViewType(int position) {
        return mManager.getItemViewType(mDatas.get(position), position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewTaker<T> itemViewTaker = mManager.getItemViewTaker(viewType);
        BaseViewHolder holder = BaseViewHolder.createViewHolder(mContext, parent, itemViewTaker.getLayoutId());
        mManager.addItemView(itemViewTaker, holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        convert(holder, mDatas.get(position), position);
    }

    private void convert(BaseViewHolder holder, T t, int position) {
        mManager.convert(holder, t, position);
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
