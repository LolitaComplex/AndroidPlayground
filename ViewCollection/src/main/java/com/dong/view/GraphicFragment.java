package com.dong.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dong.tool.ShowToast;
import com.dong.viewcollection.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Dy on 2016/5/18.
 *
 */
public class GraphicFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            InputStream in = getResources().getAssets().open("thb_1.mp3");
            int available = in.available();
            ShowToast.showText(mContext,available+"", Toast.LENGTH_SHORT);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_graphic,container,false);
    }
}
