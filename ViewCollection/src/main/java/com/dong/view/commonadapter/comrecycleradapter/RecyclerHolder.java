package com.dong.view.commonadapter.comrecycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dong.viewcollection.R;

/**
 * Created by Doing on 2016/9/12.
 *
 */
public class RecyclerHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;

    private View mContentView;
    private Context mContext;

    private RecyclerHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        this.mContext = context;
        this.mContentView = itemView;
        mContentView.setClickable(true);
        mContentView.setBackgroundResource(R.drawable.recyclerview_touch_bg);
        mViews = new SparseArray<>();
    }

    public static RecyclerHolder get(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new RecyclerHolder(context, itemView, parent);
    }

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

    // ================= 添加一些便利的方法,以后逐步完善 =====================

    public void setText(int viewId, String text) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(text);
        }
    }
}
