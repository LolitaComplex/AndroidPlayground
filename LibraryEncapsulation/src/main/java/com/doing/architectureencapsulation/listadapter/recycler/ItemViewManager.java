package com.doing.architectureencapsulation.listadapter.recycler;

import android.util.SparseArray;

/**
 * 多Item管理者
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-30.
 *
 * 我们首先需要根据position获取到ItemType对应的具体实现，要提供这个方法
 * 其次我们要根据ViewType获取对应的具体实现，提供这个方法
 *
 * 可以想象每一个ItemType都是与每一个具体实现一一对象以你的，可以使用SpareArray存储
 */

public class ItemViewManager<T> {

    private SparseArray<ItemViewTaker<T>> mTakerArray = new SparseArray<>();
    private SparseArray<ItemViewTaker<T>> mHolderToTakerArray = new SparseArray<>();
    private int type = 0;


    public int getItemViewType(T data, int position){
        for (int x = 0; x < mTakerArray.size(); x++){
            ItemViewTaker<T> taker = mTakerArray.get(x);
            if (taker.getItemViewType(data, position)) {
                return x;
            }
        }
        return 0;
    }



    public ItemViewTaker<T> getItemViewTaker(int viewType) {
        return mTakerArray.get(viewType);
    }

    public void convert(BaseViewHolder holder, T data, int position) {
        mHolderToTakerArray.get(holder.hashCode()).convert(holder, data, position);
    }

    public void addItemView(ItemViewTaker<T> taker){
        mTakerArray.put(type++, taker);
    }

    public void addItemView(ItemViewTaker<T> taker, BaseViewHolder holder){
        mHolderToTakerArray.put(holder.hashCode(), taker);
    }

}
