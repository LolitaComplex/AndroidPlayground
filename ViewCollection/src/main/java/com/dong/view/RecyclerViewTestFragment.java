package com.dong.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dong.adapter.GalleryAdapter;
import com.dong.lib.utils.ToastUtil;
import com.dong.viewcollection.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 杜营 on 2016/6/21.
 *
 */
public class RecyclerViewTestFragment extends BaseFragment {

    private View inflate;

    private List<Integer> data = Arrays.asList(R.mipmap.pagecontent_1,R.mipmap.pagecontent_2,R.mipmap.pagecontent_3,R.mipmap.happy_new_year_1,
            R.mipmap.happy_new_year_2,R.mipmap.happy_new_year_3,R.mipmap.happy_new_year_4,R.mipmap.happy_new_year_5,R.mipmap.happy_new_year_6);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        inflate = inflater.inflate(R.layout.fragment_recycler_test, container, false);
        initView();
        return inflate;
    }

    private void initView() {
        RecyclerView rv_gallery = (RecyclerView) inflate.findViewById(R.id.rv_gallery);
        final ImageView iv_big_image = (ImageView) inflate.findViewById(R.id.iv_big_image);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        rv_gallery.setLayoutManager(manager);

        GalleryAdapter adapter = new GalleryAdapter(data);
        rv_gallery.setAdapter(adapter);


        adapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.show("当前位置"+position);
                iv_big_image.setImageResource(data.get(position));
            }
        });
        iv_big_image.setImageResource(data.get(0));
    }


}
