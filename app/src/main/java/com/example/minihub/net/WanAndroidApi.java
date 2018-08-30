package com.example.minihub.net;

import com.example.minihub.bean.Article;
import com.example.minihub.bean.Collection;
import com.example.minihub.bean.Login;
import com.example.minihub.bean.Navigation;
import com.example.minihub.bean.Project;
import com.example.minihub.bean.BannerBean;
import com.example.minihub.bean.Query;
import com.example.minihub.bean.Register;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("article/list/{page}/json")
    Observable<Article> article(@Path("page")int page);

    @GET("project/list/1/json?cid=294")
    Observable<Project> project();

    @GET("lg/collect/list/{page}/json")
    Observable<Article> collection(@Path("page")int page);

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<Query> query(@Path("page")int page, @Field("k") String k);

    @GET("tree/json")
    Observable<Navigation> navigation();

    @GET("article/list/{page}/json")
    Observable<Article> navigationArticle(@Path("page")int page,@retrofit2.http.Query("cid") int cid);

}
