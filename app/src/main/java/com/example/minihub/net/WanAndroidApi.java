package com.example.minihub.net;

import com.example.minihub.bean.Article;
import com.example.minihub.bean.User;
import com.example.minihub.bean.BannerBean;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WanAndroidApi {

    @POST("users/login")
    @FormUrlEncoded
    Observable<Response<User>> login(@Field("username")String username,@Field("password")String password);

    @POST("user/register")
    @FormUrlEncoded
    Observable<Response<User>> register(@Field("username")String username,
                                        @Field("password")String password,
                                        @Field("repassword")String repassword);

    @GET("banner/json")
    Observable<BannerBean> banner();

    @GET("article/list/1/json")
    Observable<Article> article();

}
