package com.dong.view.commonadapter;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.dong.adapter.DividerItemDecoration;
import com.dong.view.BaseFragment;
import com.dong.view.commonadapter.commlistadapter.ListBean;
import com.dong.view.commonadapter.comrecycleradapter.MultiItemTypeSupport;
import com.dong.view.commonadapter.comrecycleradapter.RecyclerHolder;
import com.dong.view.commonadapter.comrecycleradapter.RecyclerMultiItemAdapter;
import com.dong.viewcollection.R;

import java.util.ArrayList;

/**
 * Created by Doing on 2016/9/13.
 *
 */
public class MultiItemRecyclerViewAdapterFragment extends BaseFragment {

    private ArrayList<ListBean> mData;

    @Override
    protected void initVariables() {
        mData = new ArrayList<>();
        mData.add(new ListBean("犀川创平", "男", 32, "抽烟"));
        mData.add(new ListBean("西之园萌绘", "女", 19, "向大叔撒娇"));
        mData.add(new ListBean("犀川创平", "男", 32, "抽烟"));
        mData.add(new ListBean("西之园萌绘", "女", 19, "向大叔撒娇"));
        mData.add(new ListBean("犀川创平", "男", 32, "抽烟"));
        mData.add(new ListBean("西之园萌绘", "女", 19, "向大叔撒娇"));
        mData.add(new ListBean("犀川创平", "男", 32, "抽烟"));
        mData.add(new ListBean("西之园萌绘", "女", 19, "向大叔撒娇"));
        mData.add(new ListBean("犀川创平", "男", 32, "抽烟"));
        mData.add(new ListBean("西之园萌绘", "女", 19, "向大叔撒娇"));
        mData.add(new ListBean("犀川创平", "男", 32, "抽烟"));
        mData.add(new ListBean("西之园萌绘", "女", 19, "向大叔撒娇"));
        mData.add(new ListBean("犀川创平", "男", 32, "抽烟"));
        mData.add(new ListBean("西之园萌绘", "女", 19, "向大叔撒娇"));
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(mContext);
        recyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));


        MultiItemTypeSupport<ListBean> support = new MultiItemTypeSupport<ListBean>() {


            @Override
            public int getLayoutId(int itemType) {
                if (itemType == 0) {
                    return R.layout.item_multi_item_0;
                }
                return R.layout.item_multi_item_1;
            }

            @Override
            public int getItemViewType(int position, ListBean listBean) {
                if(position % 2 == 0){
                    return 0;
                }
                return 1;
            }
        };


        recyclerView.setAdapter(new RecyclerMultiItemAdapter<ListBean>(mContext,mData,support) {

            @Override
            public void convert(RecyclerHolder holder, ListBean data) {
                holder.setText(R.id.ListViewAdapter_tv_name, "姓名：" + data.getName());
                holder.setText(R.id.ListViewAdapter_tv_age, "年龄：" + data.getAge());
                holder.setText(R.id.ListViewAdapter_tv_sex, "性别：" + data.getSex());
                holder.setText(R.id.ListViewAdapter_tv_hobby, "爱好：" + data.getHobby());
            }
        });
        return recyclerView;
    }
}
