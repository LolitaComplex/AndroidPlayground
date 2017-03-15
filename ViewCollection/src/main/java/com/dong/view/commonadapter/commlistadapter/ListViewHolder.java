package com.dong.view.commonadapter.commlistadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Doing on 2016/9/12.
 *
 * 其实也简单，就是把过去存为成员变量的控件们集体保存在了一个容器中，通过Id保存并且保证随时能够获取。不过确实很优雅 ``>▽<``
 */
public class ListViewHolder {

    private final SparseArray<View> mViews;

    private View mContentView;

    private ListViewHolder(Context context, ViewGroup parent, int layoutId, int position) {

        this.mViews = new SparseArray<>();
        mContentView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mContentView.setTag(this);
    }

    /**
     * 初始化ViewHolder
     */
    public static ListViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ListViewHolder(context, parent, layoutId, position);
        }
        return (ListViewHolder) convertView.getTag();
    }

    /**
     * 获取控件，这个泛型写的可以学习，这样书写调用getView后不用强转，写的很优雅
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getContentView() {
        return mContentView;
    }

    //=========== 以下方法其实只是便利的方法而已，很实用 =============

    public ListViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public ListViewHolder setImageResource(int viewId, int drawableId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(drawableId);
        return this;
    }

    public ListViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    //=========== 缺少一个常用的setImageUrl,等比较几款常用的图片加载框架后再写吧 ============
    //TODO
}

/**
 *  使用这个通用ListViewHolder的效果，是不是很优雅呢？
 *  public View getView(int position, View convertView, ViewGroup parent){
         ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,R.layout.item_single_str, position);
         TextView tv = viewHolder.getView(R.id.id_tv_title);
         tv.setText(mDatas.get(position));
         return viewHolder.getConvertView();
    }
 */
