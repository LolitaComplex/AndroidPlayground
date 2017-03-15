package com.dong.view;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dong.bean.GithubService;
import com.dong.bean.Repos;
import com.dong.bean.User;
import com.dong.lib.utils.LogUtils;
import com.dong.lib.utils.ToastUtil;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 杜营 on 2016/8/10.
 *
 */
public class RetrofitFragment extends BaseFragment {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(mContext);
        textView.setText("RetrofitFragment");
        textView.setTextColor(Color.BLUE);
        textView.setPadding(50, 40, 50, 40);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(10.0f);
        drawable.setStroke(5, Color.RED);
        textView.setBackground(drawable);
        return textView;
    }

    @Override
    protected void loadData(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubService githubService = retrofit.create(GithubService.class);

        Call<Repos> octocat = githubService.listRepos("octocat");

        octocat.enqueue(new Callback<Repos>() {
            @Override
            public void onResponse(Call<Repos> call, Response<Repos> response) {
                LogUtils.d(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<Repos> call, Throwable t) {
                ToastUtil.show("网络异常");
            }
        });

        Retrofit post = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubService service = post.create(GithubService.class);
        Call<User> user = service.createUser(new User("Doing", "male"));
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}
