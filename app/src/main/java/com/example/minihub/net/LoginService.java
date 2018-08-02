package com.example.minihub.net;

import android.support.annotation.NonNull;

import com.example.minihub.User;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginService {

    @GET("users/{user}")
    Observable<Response<User>> login(@Path("user")String user);

    @POST("users/{user}")
    Observable<Call<User>> login2(@Path("user")String user);
}
