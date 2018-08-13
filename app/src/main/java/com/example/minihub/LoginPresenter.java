package com.example.minihub;


import android.content.Intent;
import android.util.Log;

import com.example.minihub.bean.User;
import com.example.minihub.net.WanAndroidApi;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoginPresenter<V extends LoginContract.LoginView> implements LoginContract.LoginPresenter<V> {

    private V mView;
    private User user;

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

//        Observable<Response<User>> observable = AppRetrofit.INSTANCE.getRetrofit(mView.getContext())
//                .create(WanAndroidApi.class)
//                .login(user,password);
//
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Response<User>>() {
//                    User user;
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Response<User> userResponse) {
//                          user = userResponse.body();
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("Error",e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e(LoginPresenter.class.getName(),user.getAvatar_url());
//                        Intent intent = new Intent(mView.getContext(),MainActivity.class);
//                        intent.putExtra("user",user.getLogin());
//                        mView.startNextActivity(intent);
//                    }
//                });
                        Intent intent = new Intent(mView.getContext(),MainActivity.class);
                        mView.startNextActivity(intent);

    }


}
