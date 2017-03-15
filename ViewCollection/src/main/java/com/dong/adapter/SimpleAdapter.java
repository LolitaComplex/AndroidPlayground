package com.dong.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dong.lib.utils.UIUtils;
import com.dong.viewcollection.R;

import java.util.List;

/**
 * Created by 杜营 on 2016/8/25.
 *
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {


    private List<String> mData;

    private OnSimpleItemClickListener mListener;

    public SimpleAdapter(List<String> data) {
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.view.View inflate = UIUtils.inflate(android.R.layout.simple_list_item_1, parent);
        ViewHolder holder = new ViewHolder(inflate);

        holder.content = (TextView) inflate.findViewById(android.R.id.text1);
        holder.content.setTextColor(Color.BLUE);
        holder.content.setTextSize(20);
        holder.content.setBackgroundResource(R.drawable.recyclerview_touch_bg);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.content.setText(mData.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onItemClick(v, position);
            }
        });
    }

    public List<String> getData() {
        return mData;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnSimpleItemClickListener(OnSimpleItemClickListener listener) {
        this.mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public TextView content;
    }

    public interface OnSimpleItemClickListener{
        void onItemClick(View view, int position);
    }
}
