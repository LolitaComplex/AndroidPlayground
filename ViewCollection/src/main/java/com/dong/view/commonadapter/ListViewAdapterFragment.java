package com.dong.view.commonadapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;

import com.dong.view.BaseFragment;
import com.dong.view.commonadapter.commlistadapter.ListBean;
import com.dong.view.commonadapter.commlistadapter.ListViewBaseAdapter;
import com.dong.view.commonadapter.commlistadapter.ListViewHolder;
import com.dong.viewcollection.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Doing on 2016/9/12.
 *
 */
public class ListViewAdapterFragment extends BaseFragment {

    private List<ListBean> mData;

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
        ListView listView = new ListView(mContext);
        listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        listView.setAdapter(new ListViewBaseAdapter<ListBean>(mContext,mData,R.layout.item_listview_common_test) {
            @Override
            protected void convert(ListViewHolder holder, ListBean dataItem) {
                holder.setText(R.id.ListViewAdapter_tv_name, "姓名：" + dataItem.getName());
                holder.setText(R.id.ListViewAdapter_tv_sex, "性别：" + dataItem.getSex());
                holder.setText(R.id.ListViewAdapter_tv_age, "年龄：" + dataItem.getAge());
                holder.setText(R.id.ListViewAdapter_tv_hobby, "爱好：" + dataItem.getHobby());
            }
        });
        return listView;
    }
}
