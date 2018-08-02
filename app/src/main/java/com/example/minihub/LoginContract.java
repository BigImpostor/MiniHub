package com.example.minihub;

import android.content.Context;
import android.content.Intent;

public class LoginContract {
    interface LoginView{
        Context getContext();
        void startNextActivity(Intent intent);
    }

    interface LoginPresenter<T extends LoginContract.LoginView>{
        void attachView(T view);
        void detachView();
        void login(String user, String password);
    }
}
