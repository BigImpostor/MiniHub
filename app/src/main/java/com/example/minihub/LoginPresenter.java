package com.example.minihub;


import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.minihub.activity.MainActivity;
import com.example.minihub.bean.Login;
import com.example.minihub.bean.Register;
import com.example.minihub.net.AppRetrofit;
import com.example.minihub.net.WanAndroidApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class LoginPresenter<V extends LoginContract.LoginView> implements LoginContract.LoginPresenter<V> {

    private V mView;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mCompositeDisposable.clear();
    }

    @Override
    public void login(String user, String password) {
        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(password)){
            Toast.makeText(mView.getContext(),"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(user.length() < 6 || password.length() < 6){
            Toast.makeText(mView.getContext(),"用户名和密码长度不能小于6",Toast.LENGTH_SHORT).show();
            return;
        }

        Observable<Login> observable = AppRetrofit.INSTANCE
                                                    .getRetrofit(mView.getContext())
                                                    .create(WanAndroidApi.class)
                                                    .login(user,password);
        Disposable disposable = observable.observeOn(AndroidSchedulers.mainThread())
                                            .subscribeOn(Schedulers.io())
                                            .filter(new Predicate<Login>() {
                                                @Override
                                                public boolean test(Login login) throws Exception {
                                                    if(login.getErrorCode() != 0){
                                                        return false;
                                                    }
                                                    return true;
                                                }
                                            })
                                            .subscribeWith(new SimplifyObserver<Login>(){
                                                @Override
                                                public void onError(Throwable e) {
                                                    super.onError(e);
                                                    Log.e("LoginPresenter", e.toString());
                                                }

                                                @Override
                                                public void onComplete() {
                                                    super.onComplete();
                                                    Intent intent = new Intent(mView.getContext(), MainActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    mView.startNextActivity(intent);
                                                }
                                            });

        addSubscribe(disposable);
    }

    public void register(String username, String password, String repassword){
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)){
            Toast.makeText(mView.getContext(),"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.length() < 6 || password.length() < 6 || repassword.length() < 6){
            Toast.makeText(mView.getContext(),"用户名和密码长度不能小于6",Toast.LENGTH_SHORT).show();
            return;
        }

        if(repassword != password){
            Toast.makeText(mView.getContext(),"密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }

        Observable<Register> observable = AppRetrofit.INSTANCE.getRetrofit(mView.getContext())
                                                        .create(WanAndroidApi.class)
                                                        .register(username,password,repassword);

        Disposable  disposable = observable.subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .filter(new Predicate<Register>() {
                                                         @Override
                                                         public boolean test(Register register) throws Exception {
                                                             if(register.getErrorCode() != 0)
                                                                    return false;
                                                             return true;
                                                         }
                                            }).subscribeWith(new SimplifyObserver<Register>() {
                                                                @Override
                                                                public void onComplete() {
                                                                    super.onComplete();
                                                                    Intent intent = new Intent(mView.getContext(),MainActivity.class);
                                                                    mView.startNextActivity(intent);
                                                                }
                                            });

        addSubscribe(disposable);
    }


    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }
}
