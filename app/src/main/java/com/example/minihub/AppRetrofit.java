package com.example.minihub;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public enum AppRetrofit {

    INSTANCE;

    public Retrofit getRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {

            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }


}
