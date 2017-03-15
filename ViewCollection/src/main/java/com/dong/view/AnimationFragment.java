package com.dong.view;

import android.os.Bundle;

import com.dong.menum.AnimationViewType;
import com.dong.menum.ViewConllectionType;

/**
 * Created by 杜营 on 2016/6/6.
 *
 */
public class AnimationFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null || !savedInstanceState.getBoolean("isData")){
            AnimationViewType[] viewTypeArr = AnimationViewType.values();
            ListFragment<AnimationViewType> fragment = ListFragment.newInstance(viewTypeArr);
            getFragmentManager().popBackStack();
            mContext.addFragment(fragment,"AnimationListFragment",true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isData",true);
    }
}
