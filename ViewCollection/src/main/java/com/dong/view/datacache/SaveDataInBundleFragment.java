package com.dong.view.datacache;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dong.view.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Dy on 2016/5/23.
 *
 */
public class SaveDataInBundleFragment extends BaseFragment {

    private ListView listView;
    private ArrayList<String> mData;
    private DialogFragment mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listView = new ListView(mContext);
        initData(savedInstanceState);
        return listView;
    }

    private void initData(Bundle savedInstanceState){
        //是否是通过旋转屏幕达到的onCreatView()方法
        if(savedInstanceState != null){
            mData = savedInstanceState.getStringArrayList("mData");
        }

        if(mData == null){
            mDialog = new DialogFragment() {
                @NonNull
                @Override
                public Dialog onCreateDialog(Bundle savedInstanceState) {
                    ProgressDialog progressDialog = new ProgressDialog(mContext);
                    return progressDialog;
                }
            };
            mDialog.show(getFragmentManager(), "ProgressDialog");
            mDialog.setCancelable(false);
            GetDataTask task = new GetDataTask();
            task.execute();
        }else{
            initAdapter();
        }
    }

    private void initAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,android.R.layout.simple_list_item_1,mData);
        listView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("mData", mData);//保存数据
        Toast.makeText(mContext,"onSaveInstanceState()",Toast.LENGTH_LONG).show();
    }

    private class GetDataTask extends AsyncTask<Void,Void,ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            SystemClock.sleep(3000);
            return new ArrayList<>(Arrays.asList("博丽灵梦", "雾雨魔理沙", "帕秋莉诺蕾姬", "芙兰朵珞斯卡雷特", "蕾米莉亚斯卡雷特", "八云紫", "藤原妹红", "蓬莱山辉夜", "古明地恋", "八坂神奈子", "诹访子"));
        }

        @Override
        protected void onPostExecute(ArrayList<String> listName) {
            mData = listName;
            initAdapter();
            mDialog.dismiss();
        }
    }
}
