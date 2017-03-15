package com.dong.view;

import android.os.Bundle;

import com.dong.menum.DataCacheViewType;

/**
 * Created by Dy on 2016/5/20.
 *
 */
public class DataCacheFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState== null || !savedInstanceState.getBoolean("isData",false)){
            DataCacheViewType[] values = DataCacheViewType.values();
            ListFragment listFragment = ListFragment.newInstance(values);
            getFragmentManager().popBackStack();
            mContext.addFragment(listFragment, listFragment.getFragmentName(), true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isData",true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
