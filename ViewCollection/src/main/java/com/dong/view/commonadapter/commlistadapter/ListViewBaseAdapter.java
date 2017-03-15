package com.dong.view.commonadapter.commlistadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Doing on 2016/9/12.
 *
 */
public abstract class ListViewBaseAdapter<T> extends BaseAdapter {

    protected List<T> mData;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected int mLayoutId;

    public ListViewBaseAdapter(Context context, List<T> data, int layoutId) {
        this.mLayoutId = layoutId;
        this.mContext = context;
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder holder = getViewHolder(position, convertView, parent);
        convert(holder, getItem(position));
        return holder.getContentView();
    }

    protected abstract void convert(ListViewHolder holder, T dataItem);

    /**
     * 这个方法设计的理念我认为是实现高内距？实现插拔式设计，那么为什么是私有方法呢？
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    private ListViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ListViewHolder.get(mContext, convertView, parent, mLayoutId, position);
    }
}
