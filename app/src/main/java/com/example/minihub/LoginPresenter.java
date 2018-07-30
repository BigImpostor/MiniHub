package com.example.minihub;

import android.content.Intent;

import com.example.minihub.net.LoginService;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginPresenter<V extends LoginContract.LoginView> implements LoginContract.LoginPresenter<V> {

    private V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void login(String user, String password) {

        Observable<Response<User>> observable = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .build()
                .create(LoginService.class)
                .login(user);

        Subscriber<Response<User>> subscriber = new Subscriber<Response<User>>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Response<User> userResponse) {
                User user = userResponse.body();
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
