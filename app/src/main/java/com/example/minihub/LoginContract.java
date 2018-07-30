package com.example.minihub;

public class LoginContract {
    interface LoginView{

    }

    interface LoginPresenter<T extends LoginContract.LoginView>{
        void attachView(T view);
        void detachView();
        void login(String user, String password);
    }
}
