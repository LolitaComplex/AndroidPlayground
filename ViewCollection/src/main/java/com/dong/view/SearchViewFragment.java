package com.dong.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.dong.viewcollection.MainActivity;
import com.dong.viewcollection.R;

/**
 * Created by Dy on 2016/2/25.
 */
public class SearchViewFragment extends BaseFragment {

    private SearchView searchView;
    private ListView listView;

    private String[] mBufString = {"Alert","Baby","Cat","Dad","Easy","Fate","Give"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_searchview,container,false);

        findViewById(view);
        initView();

        return view;
    }

    private void findViewById(View view) {
        listView = (ListView) view.findViewById(R.id.lv);
        searchView = (SearchView) view.findViewById(R.id.sv);
    }

    private void initView() {
        listView.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mBufString));
        listView.setTextFilterEnabled(true);


        searchView.setIconifiedByDefault(true);//默认缩小为图标
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(mContext, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    listView.clearTextFilter();
                } else {
                    listView.setFilterText(newText);
                }

                return true;
            }
        });
    }
}
