package com.dong.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dong.menum.ViewType;
import com.dong.viewcollection.R;

/**
 * Created by Dy on 2016/2/26.
 *
 */
public class TabHostFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_tabhostfragment,container,false);

        FragmentTabHost tabHost = (FragmentTabHost) view.findViewById(R.id.tabhost);

        tabHost.setup(mContext, getChildFragmentManager(), R.id.realContent);

        tabHost.addTab(tabHost.newTabSpec("Spinner").setIndicator("Spinner"), SpinnerFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("Error").setIndicator("Error"),ErrorFragment.class,null);

        ViewType[] values = ViewType.values();
        String[] bufViewName = new String[values.length];
        for(int x=0; x<values.length; x++){
            bufViewName[x] = values[x].name();
        }
        Bundle bundle = new Bundle();
        bundle.putStringArray("ViewData", bufViewName);

        tabHost.addTab(tabHost.newTabSpec("List").setIndicator("ListFragment"), ListFragment.class, bundle);
        tabHost.addTab(tabHost.newTabSpec("SearchView").setIndicator("SearchView"),SearchViewFragment.class,null);

        //tabHost.addTab(tabHost.newTabSpec("").setIndicator(view));可以这样设置Tab的样式
        return view;
    }


    /**
     * 这是TabHost在TabActivity中的写法
     */

    /*private TabHost mTabHost;
    private LinearLayout llTabl;
    private LinearLayout llTab2;
    private LinearLayout llTab3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_tabhost,null);

        findViewById(view);
        initView(view);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initView(View view){
        mTabHost.setup(mContext.getLo);
        TabHost.TabSpec tab1 = mTabHost.newTabSpec("tab1").setIndicator("我的世界").setContent(R.id.ll_tab1);
        mTabHost.addTab(tab1);
        TabHost.TabSpec tab2 = mTabHost.newTabSpec("tab1").setIndicator("你的世界").setContent(R.id.ll_tab2);
        mTabHost.addTab(tab2);
        TabHost.TabSpec tab3 = mTabHost.newTabSpec("tab1").setIndicator("Dark Fragme and Dark").setContent(R.id.ll_tab3);
        mTabHost.addTab(tab3);

        initLayout(llTabl);
        initLayout(llTab2);
        initLayout(llTab3);
    }

    private int layoutColorFlag = 0;

    //填充三个布局的内容
    private void initLayout(ViewGroup viewGroup){
        TextView textView = new TextView(mContext);
        textView.setText(viewGroup.getId() + "");
        textView.setTextSize(30.0f);
        if(layoutColorFlag == 0){
            textView.setTextColor(Color.GREEN);
        }else if(layoutColorFlag == 1){
            textView.setTextColor(Color.BLUE);
        }else{
            textView.setTextColor(Color.YELLOW);
        }
        viewGroup.addView(textView);
        layoutColorFlag++;
    }

    private void findViewById(View view) {
        mTabHost = (TabHost) view.findViewById(R.id.tabHost);

        //获取三个显示布局的View
        llTabl = (LinearLayout) view.findViewById(R.id.ll_tab1);
        llTab2 = (LinearLayout) view.findViewById(R.id.ll_tab2);
        llTab3 = (LinearLayout) view.findViewById(R.id.ll_tab3);
    }*/
}
