package com.dong.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dong.menum.AnimationViewType;
import com.dong.menum.DataCacheViewType;
import com.dong.menum.ViewConllectionType;
import com.dong.menum.ViewType;

import java.util.List;

/**
 * Created by Dy on 2016/2/25.
 *
 */
public class ListFragment<T extends ViewConllectionType> extends BaseFragment {

    private String[] mBufViewName;
    private T[] mValues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView  lv_collection = new ListView(mContext);
        lv_collection.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));

        initData();
        initView(lv_collection);

        return lv_collection;

    }

    private void initData() {
        mBufViewName = getArguments().getStringArray("ViewData");
        mValues = (T[]) getArguments().getSerializable("ViewType");
    }

    private void initView(ListView lv_collection) {
        lv_collection.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mBufViewName));
        lv_collection.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mValues == null) {
                    return;
                }

                String className = mValues[position].getTypeClassName();
                try {
                    Class<?> viewClass = Class.forName(className);
                    BaseFragment fragment = (BaseFragment) viewClass.newInstance();
                    mContext.addFragment(fragment, fragment.getFragmentName(), true);
                } catch (Exception e) {
                    BaseFragment fragment = new ErrorFragment();
                    mContext.addFragment(fragment, fragment.getFragmentName(), true);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static <E extends ViewConllectionType> ListFragment<E> newInstance(E[] viewType) {
        ListFragment<E> fragment = new ListFragment<>();

        String[] mBufViewData = new String[viewType.length];
        for (int x = 0; x < viewType.length; x++) {
            mBufViewData[x] = viewType[x].name();
        }
        Bundle bundle = new Bundle();
        bundle.putStringArray("ViewData", mBufViewData);
        bundle.putSerializable("ViewType", viewType);

        fragment.setArguments(bundle);
        return fragment;
    }
}
