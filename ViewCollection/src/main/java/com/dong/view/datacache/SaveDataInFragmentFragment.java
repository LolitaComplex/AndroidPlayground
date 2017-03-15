package com.dong.view.datacache;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.dong.view.BaseFragment;


/**
 * Created by Dy on 2016/6/3.
 *
 */
public class SaveDataInFragmentFragment extends BaseFragment {

    private ImageView mImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mImageView = new ImageView(mContext);
        mImageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        initData();
        return mImageView;
    }

    private void initData() {
        final DataFragment retainFragment = (DataFragment) getFragmentManager().findFragmentByTag("retainFragment");
        Bitmap bitmap = null;
        if(retainFragment != null){
            bitmap = retainFragment.getBitmap();
        }else{
            getFragmentManager().beginTransaction().add(new DataFragment(),"retainFragment").commit();
        }

        if(bitmap!=null){
            initView(bitmap);
        }else{
            final DialogFragment dialog = new DialogFragment(){
                @NonNull
                @Override
                public Dialog onCreateDialog(Bundle savedInstanceState) {
                    ProgressDialog progressDialog = new ProgressDialog(mContext);
                    progressDialog.setTitle("正在获取数据，请稍后");
                    return progressDialog;
                }
            };
            dialog.setCancelable(false);
            dialog.show(getFragmentManager(),"dialog");

            RequestQueue queue = Volley.newRequestQueue(mContext);
            String imgeUrl = "http://upload-images.jianshu.io/upload_images/1816914-7f04938d78783883.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
            ImageRequest request = new ImageRequest(imgeUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    initView(response);
                    retainFragment.setBitmap(response);
                    dialog.dismiss();
                }
            }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mContext,"连接网络异常",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            queue.add(request);
        }
    }

    private void initView(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }


}
