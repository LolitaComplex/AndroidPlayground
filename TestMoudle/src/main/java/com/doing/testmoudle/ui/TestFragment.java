package com.doing.testmoudle.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doing.testmoudle.R;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-22.
 */

public class TestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_test, container, false);
    }

}
