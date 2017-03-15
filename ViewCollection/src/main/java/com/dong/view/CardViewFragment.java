package com.dong.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dong.lib.utils.UIUtils;
import com.dong.viewcollection.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.w3c.dom.Text;

import java.util.Arrays;

/**
 * Created by Doing on 2016/9/20.
 *
 */
public class CardViewFragment extends BaseFragment {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_cardview, container, false);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new CommonAdapter<String>(mContext, R.layout.cardivew_recycler, Arrays.asList("1","2")) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                ViewGroup.LayoutParams layoutParams = holder.getConvertView().getLayoutParams();

                layoutParams.width = 500;

                holder.getConvertView().setLayoutParams(layoutParams);

                holder.setImageResource(R.id.CardViewItem_iv_title, R.mipmap.happy_new_year_5);
                holder.setText(R.id.CardViewItem_tv_title, "当前位置" + position);
            }
        });


        return recyclerView;
    }
}
