package com.dong.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dong.viewcollection.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 杜营 on 2016/6/30.
 *
 */
public class VolleyFragment extends BaseFragment implements View.OnClickListener {

    private static final String URL = "http://live.bilibili.com/AppIndex/home?_device=android&_hwid=51e96f5f2f54d5f9&_ulv=10000&access_key=563d6046f06289cbdcb472601ce5a761&appkey=c1b107428d337928&build=410000&platform=android&scale=xxhdpi&sign=fbdcfe141853f7e2c84c4d401f6a8758";
    private static final String JSON_URL = "http://live.bilibili.com/AppNewIndex/common?_device=android&platform=android&scale=xxhdpi";
    private static final String IMAGE_URL = "http://imgsrc.baidu.com/forum/w%3D580/sign=db39a59f29dda3cc0be4b82831e93905/43e8c8afa40f4bfb6be2a254044f78f0f63618ee.jpg";
    private TextView tv_content;
    private TextView tv_title;
    private ImageView iv_content;
    private RequestQueue mRequestQueue;
    private ImageLoader loder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_volley, container, false);

        initView(inflate);

        initData();
        return inflate;
    }

    /**
     * _device=android
     * _hwid=9ec238cf481b1087
     * appkey=1d8b6e7d45233436
     * build=426003
     * mobi_app=android
     * platform=android
     * scale=xxhdpi
     * sign=cb01cac6b427a7bbd4a96ba47a189e9f
     */


    private void initView(View view) {
        tv_content = (TextView) view.findViewById(R.id.VolleyFragment_tv_content);
        tv_title = (TextView) view.findViewById(R.id.VolleyFragment_tv_title);
        iv_content = (ImageView) view.findViewById(R.id.VolleyFragment_iv_content);

        view.findViewById(R.id.VolleyFragment_btn_string_request).setOnClickListener(this);
        view.findViewById(R.id.VolleyFragment_btn_post).setOnClickListener(this);
        view.findViewById(R.id.VolleyFragment_btn_json_request).setOnClickListener(this);
        view.findViewById(R.id.VolleyFragment_btn_image_request).setOnClickListener(this);
        view.findViewById(R.id.VolleyFragment_btn_imageloader_request).setOnClickListener(this);
        view.findViewById(R.id.VolleyFragment_btn_network_imageview_request).setOnClickListener(this);
    }

    private void initData() {
        mRequestQueue = Volley.newRequestQueue(mContext);
        loder = new ImageLoader(mRequestQueue, new BitmapCache());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.VolleyFragment_btn_string_request:
                stringRequest();
                break;

            case R.id.VolleyFragment_btn_post:
                post();
                break;

            case R.id.VolleyFragment_btn_json_request:
                jsonRequest();
                break;

            case R.id.VolleyFragment_btn_image_request:
                imageRequest();
                break;

            case R.id.VolleyFragment_btn_imageloader_request:
                imageLoader();
                break;

            case R.id.VolleyFragment_btn_network_imageview_request:
                networdImageView();
                break;
        }
    }

    private void stringRequest() {
        String text = "StringGetRequest";
        StringRequest request = new StringRequest(URL,new SuccessListener<String>(text),new ErrorListener(text));
        mRequestQueue.add(request);
    }

    private void post() {
        String text = "StringPostRequest";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new SuccessListener<String>(text), new ErrorListener(text)){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", "布鲁马");
                params.put("password", "123456");
                return params;
            }
        };
        mRequestQueue.add(request);
    }

    private void jsonRequest() {
        String text = "JsonRequest";
        //这是JsonObjectRequest,还有一个JsonArrayRequest,
        JsonObjectRequest request = new JsonObjectRequest(JSON_URL, null, new SuccessListener<JSONObject>(text), new ErrorListener(text));
        mRequestQueue.add(request);
    }

    private void imageRequest() {
        String text = "ImageRequest";
        //图片宽高设置为0表示无论图片多大都不会对图片进行压缩。
        //ARGB_8888-->最好的颜色属性，每个像素占用4个字节
        //RGB_565-->每个像素占用2个字节
        ImageRequest request = new ImageRequest(IMAGE_URL,new SuccessListener<Bitmap>(text),0,0, Bitmap.Config.ARGB_8888,new ErrorListener(text));
        mRequestQueue.add(request);
    }

    private void imageLoader() {
        iv_content.setVisibility(View.VISIBLE);
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(iv_content, R.mipmap.pagecontent_1, R.mipmap.ic_launcher);
        loder.get(IMAGE_URL, imageListener);

    }

    private void networdImageView() {
        iv_content.setVisibility(View.VISIBLE);
        NetworkImageView networkIv = (NetworkImageView) iv_content;
        networkIv.setDefaultImageResId(R.mipmap.happy_new_year_3);
        networkIv.setErrorImageResId(R.mipmap.ic_launcher);
        networkIv.setImageUrl(IMAGE_URL,loder);
    }

    private class SuccessListener<T> implements Response.Listener<T>{
        private String text;

        public SuccessListener(String text){
            this.text = text;
        }


        @Override
        public void onResponse(T response) {
            tv_title.setText(text + " Success");
            tv_content.setText(response.toString());
            if(response instanceof android.graphics.Bitmap){
                iv_content.setVisibility(View.VISIBLE);
                iv_content.setImageBitmap((android.graphics.Bitmap) response);
            }else{
                iv_content.setVisibility(View.GONE);
            }
        }
    }

    private class ErrorListener implements Response.ErrorListener{

        private String text;

        public ErrorListener(String text){
            this.text = text;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            tv_title.setText(text + " Failure");
        }
    }

    private class BitmapCache implements ImageLoader.ImageCache{

        private LruCache<String,Bitmap> mCache;

        public BitmapCache(){
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String,Bitmap>(maxSize){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }
    }
}
