package com.dong.bean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by 杜营 on 2016/8/10.
 *
 */
public interface GithubService{
    @GET("users/{user}")
    Call<Repos> listRepos(@Path("user")String user);

    @POST("users/new")
    Call<User> createUser(@Body User user);
}
