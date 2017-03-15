package com.dong.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dong.lib.utils.LogUtils;
import com.dong.tool.ShowToast;
import com.dong.viewcollection.ActivityCallback;
import com.dong.viewcollection.MainActivity;

import java.io.IOException;

import butterknife.ButterKnife;

/**
 * Created by Dy on 2016/2/25.
 *
 */
public class BaseFragment extends Fragment {

    protected static String TAG;
    public MainActivity mContext;

    public String getFragmentName(){
        return getClass().getName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        TAG = getFragmentName();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initVariables();

        View view = initView(inflater,container,savedInstanceState);

        if(view != null)
            ButterKnife.bind(this, view);

        loadData();
        return view;
    }


    protected void initVariables() {

    }

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    protected void loadData(){

    }

    @Override
    public void onStart() {
        super.onStart();
        mContext.setSelectedFragment(this);
    }

    public boolean onBackPressed() {
        return false;
    }

    public boolean onWindowFoucuChange(){
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

}
