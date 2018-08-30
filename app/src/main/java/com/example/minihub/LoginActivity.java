package com.example.minihub;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.minihub.activity.MainActivity;
import com.example.minihub.net.AppRetrofit;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView,RegisterDialog.OnRegisterListener {

    private EditText idEdit;
    private EditText passwordEdit;
    private Button commitBtn;
    private Button registerBtn;

    private String user;
    private String psswrd;

    private LoginPresenter mPresenter;

    private static final String Tag = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
        initView();
        initAction();
        if (isHasCookie()){
            startActivity(new Intent(this,MainActivity.class));
        }

    }

    private boolean isHasCookie(){
        return AppRetrofit.INSTANCE.isHasCookie(getContext());
    }


    private void initView(){
        idEdit = findViewById(R.id.edit_id);
        passwordEdit = findViewById(R.id.edit_password);
        commitBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);
    }

    private void initAction(){
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = idEdit.getText().toString();
                psswrd = passwordEdit.getText().toString();
                mPresenter.login(user,psswrd);
                Log.e(LoginActivity.class.getName(),user);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterDialog dialog = new RegisterDialog(getContext(),LoginActivity.this);
                dialog.show();
            }
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void startNextActivity(Intent intent) {
        startActivity(intent);
    }


    @Override
    public void register(String usrname, String password, String repassword) {
        Log.e(Tag,"usrname" + usrname + ";" + "passsword:" + psswrd+";"+"repassword:"+repassword);
        mPresenter.register(usrname,password,repassword);
    }
}
