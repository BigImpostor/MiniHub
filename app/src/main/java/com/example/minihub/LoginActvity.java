package com.example.minihub;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.minihub.net.LoginService;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActvity extends AppCompatActivity implements LoginContract.LoginView {

    private EditText idEdit;
    private EditText psswrdEdit;
    private Button commitBtn;

    private String user;
    private String psswrd;

    private LoginPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
        initView();
        initAction();
    }

    private void initView(){
        idEdit = findViewById(R.id.edit_id);
        psswrdEdit = findViewById(R.id.edit_password);
        commitBtn = findViewById(R.id.login_btn);
    }

    private void initAction(){
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = idEdit.getText().toString();
                psswrd = psswrdEdit.getText().toString();
                mPresenter.login(user,psswrd);
                Log.e(LoginActvity.class.getName(),user);
            }
        });

    }

    /**
     * LoginContract.View
     * @return
     */

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void startNextActivity(Intent intent) {
        startActivity(intent);
    }
}
