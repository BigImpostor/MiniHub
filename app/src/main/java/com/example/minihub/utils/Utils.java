package com.example.minihub.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import com.example.minihub.MyApplication;

public class Utils {

    /**
     * 检查是否有可用网络
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null;
    }


    public static int dp2px(float dpValue) {
        final float scale = MyApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
