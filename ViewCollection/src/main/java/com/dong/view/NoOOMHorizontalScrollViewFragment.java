package com.dong.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dong.viewcollection.R;
import com.dong.widget.HorizontalScrollViewAdapter;
import com.dong.widget.NoOOMHorzitalScrollView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 杜营 on 2016/6/16.
 *
 */
public class NoOOMHorizontalScrollViewFragment extends BaseFragment{

    private int[] dataBuf;
    private View inflate;
    private List<Integer> data = Arrays.asList(R.mipmap.happy_new_year_1,R.mipmap.happy_new_year_2,R.mipmap.happy_new_year_3,
            R.mipmap.happy_new_year_4,R.mipmap.happy_new_year_5,R.mipmap.happy_new_year_6,R.mipmap.pagecontent_1,R.mipmap.pagecontent_2,
            R.mipmap.pagecontent_3);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_horizontal_scrollview, container, false);
        initView();
        return inflate;
    }

    private void initView() {
        NoOOMHorzitalScrollView scrollView = (NoOOMHorzitalScrollView) inflate.findViewById(R.id.hsv_scrollview);
        final ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_big_image);

        scrollView.setOnItemCLickListener(new NoOOMHorzitalScrollView.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                imageView.setImageResource(data.get(pos));
            }
        });

        scrollView.setOnCurrentImageChangeListener(new NoOOMHorzitalScrollView.CurrentImageChangeListener() {
            @Override
            public void onCurrentImageChanged(int position, View viewindicator) {
                imageView.setImageResource(data.get(position));
            }
        });
        imageView.setImageResource(data.get(0));
        scrollView.setAdapter(new HorizontalScrollViewAdapter(data));
    }
}
