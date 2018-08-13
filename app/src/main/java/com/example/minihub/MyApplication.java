package com.example.minihub;

import android.app.Application;
import android.content.Context;

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
