package com.dong.view;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.HttpClientStack;
import com.dong.adapter.GalleryAdapter;
import com.dong.viewcollection.R;
import com.dong.widget.RecyclerGallery;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 杜营 on 2016/6/21.
 *
 */
public class RecyclerViewGalleryFragment extends BaseFragment {

    private View inflate;
    private List<Integer> data = Arrays.asList(R.mipmap.happy_new_year_1, R.mipmap.happy_new_year_2, R.mipmap.happy_new_year_3,
            R.mipmap.happy_new_year_4, R.mipmap.happy_new_year_5, R.mipmap.happy_new_year_6, R.mipmap.pagecontent_1, R.mipmap.pagecontent_2,
            R.mipmap.pagecontent_3);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_recycler_gallery, container, false);
        initView();
        return inflate;
    }

    private void initView() {
        RecyclerGallery rv_gallery = (RecyclerGallery) inflate.findViewById(R.id.rv_gallery);
        final ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_big_image);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_gallery.setLayoutManager(manager);

        GalleryAdapter adapter = new GalleryAdapter(data);
        rv_gallery.setAdapter(adapter);

        adapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                imageView.setImageResource(data.get(position));
            }
        });

        rv_gallery.setOnCurrentItemChangeListener(new RecyclerGallery.OnCurrentItemChangeListener() {
            @Override
            public void onItemChange(View currentView, int position) {
                //currentView.setBackgroundColor(Color.BLUE);
                imageView.setImageResource(data.get(position));
            }
        });
        imageView.setImageResource(data.get(0));
    }


}
