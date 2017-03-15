package com.dong.view;

import android.os.Bundle;
import android.view.View;

import com.dong.menum.DesignViewType;

import java.util.List;

/**
 * Created by 杜营 on 2016/7/27.
 *
 */
public class DesignFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(savedInstanceState == null || !savedInstanceState.getBoolean("isData")){
            DesignViewType[] viewTypeDesign = DesignViewType.values();
            ListFragment fragment = ListFragment.newInstance(viewTypeDesign);
            getFragmentManager().popBackStack();
            mContext.addFragment(fragment,"AnimationListFragment",true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isData", true);
    }


}
