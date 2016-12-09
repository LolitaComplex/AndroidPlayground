package com.doing.fragmentpageradapter.ui.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;

public class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    private Random mRandom;
    private int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRandom = new Random();
        position = getArguments().getInt("position");
        Log.e(TAG, "onCreate()" + "第" + position + "页");
    }

    @Override
    public String toString() {
        return "第" + position + "页";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView()" + "第" + position + "页");
        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.setBackgroundColor(getRandomColor());
        TextView textView = new TextView(getActivity());
        textView.setText("第" + position + "页");
        textView.setTextSize(30);
        textView.setTextColor(Color.BLACK);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        frameLayout.addView(textView, layoutParams);
        return frameLayout;
    }

    private int getRandomColor() {
        int a = mRandom.nextInt(233);
        int r = mRandom.nextInt(233);
        int g = mRandom.nextInt(233);
        int b = mRandom.nextInt(233);
        return Color.argb(a, r, g, b);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach()"+ "第" + position + "页");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated()"+ "第" + position + "页");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart()" + "第" + position + "页");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume()"+ "第" + position + "页");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause()" + "第" + position + "页");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop()" + "第" + position + "页");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView()" + "第" + position + "页");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy()" + "第" + position + "页");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach()" + "第" + position + "页");
    }
}
