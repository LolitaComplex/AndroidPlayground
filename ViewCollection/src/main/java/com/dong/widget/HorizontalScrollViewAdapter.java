package com.dong.widget;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dong.lib.utils.UIUtils;

import java.util.List;

/**
 * Created by 杜营 on 2016/6/16.
 *
 */
public class HorizontalScrollViewAdapter  {

    private final List<Integer> data;

    public HorizontalScrollViewAdapter(List<Integer> data){
        this.data = data;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            FrameLayout layout = new FrameLayout(UIUtils.getContext());
            layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView imageView = new ImageView(UIUtils.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(250, 250);
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.setMargins(20,20,20,20);
            layout.addView(imageView,layoutParams);
            TextView textView = new TextView(UIUtils.getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0, 10, 0, 10);
            textView.setTextColor(Color.BLACK);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            layout.addView(textView,params);

            holder =  new ViewHolder();
            convertView = layout;
            holder.imageView = imageView;
            holder.textView = textView;
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setImageResource(data.get(position));
        holder.textView.setText("当前位置"+position);

        return convertView;
    }

    class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }
}
