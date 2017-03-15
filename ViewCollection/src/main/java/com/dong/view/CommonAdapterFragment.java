package com.dong.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dong.menum.CommonAdapterViewType;

/**
 * Created by Doing on 2016/9/9.
 *
 */
public class CommonAdapterFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null || !savedInstanceState.getBoolean("isData")) {
            CommonAdapterViewType[] viewTypes = CommonAdapterViewType.values();
            ListFragment fragment = ListFragment.newInstance(viewTypes);
            getFragmentManager().popBackStack();
            mContext.addFragment(fragment,"CommonAdapterFragment",true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isData", true);
    }
}
