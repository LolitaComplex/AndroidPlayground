package com.dong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dong.lib.utils.UIUtils;
import com.dong.viewcollection.R;

import java.util.List;

/**
 * Created by 杜营 on 2016/6/21.
 *
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private List<Integer> data;

    public GalleryAdapter(List<Integer> data) {
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ImageView imageView;
        public TextView textView;
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = UIUtils.inflate(R.layout.item_recycler_gallery, parent);
        ViewHolder holder = new ViewHolder(inflate);

        holder.imageView = (ImageView) inflate.findViewById(R.id.iv_gallery_item);
        holder.textView = (TextView) inflate.findViewById(R.id.tv_gallery_item);

        return holder;
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.ViewHolder holder, final int position) {
        holder.imageView.setImageResource(data.get(position));
        holder.textView.setText("当前位置为" + position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null)
                    mListener.onItemClick(holder.imageView,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
