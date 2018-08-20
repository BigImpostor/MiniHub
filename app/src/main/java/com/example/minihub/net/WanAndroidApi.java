package com.example.minihub.net;

import com.example.minihub.bean.Article;
import com.example.minihub.bean.Collection;
import com.example.minihub.bean.Login;
import com.example.minihub.bean.Project;
import com.example.minihub.bean.BannerBean;
import com.example.minihub.bean.Register;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WanAndroidApi {

    @POST("user/login")
    @FormUrlEncoded
    Observable<Login> login(@Field("username")String username, @Field("password")String password);

    @POST("user/register")
    @FormUrlEncoded
    Observable<Register> register(@Field("username")String username,
                                  @Field("password")String password,
                                  @Field("repassword")String repassword);

    @GET("banner/json")
    Observable<BannerBean> banner();

    @GET("article/list/1/json")
    Observable<Article> article();

    @GET("project/list/1/json?cid=294")
    Observable<Project> project();

    @GET("lg/collect/list/0/json")
    Observable<Collection> collection();

}
