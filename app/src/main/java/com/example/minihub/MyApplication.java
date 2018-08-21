package com.example.minihub;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends Application {

    private static MyApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

    }

    public static synchronized MyApplication getInstance() {
        return INSTANCE;
    }



}
