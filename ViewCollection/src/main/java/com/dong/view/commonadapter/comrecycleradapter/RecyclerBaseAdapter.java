package com.dong.view.commonadapter.comrecycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dong.viewcollection.R;

import java.util.List;

/**
 * Created by Doing on 2016/9/12.
 *
 */
public abstract class RecyclerBaseAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {


    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mData;
    protected LayoutInflater mLayoutInflater;

    public RecyclerBaseAdapter(Context context, int layoutId, List<T> data) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecyclerHolder.get(mContext, parent, mLayoutId);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        convert(holder,mData.get(position));
    }

    public abstract void convert(RecyclerHolder holder, T data);

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
