package com.example.minihub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActvity extends AppCompatActivity {

    private EditText idEdit;
    private EditText psswrdEdit;
    private Button commitBtn;

    private String id,psswrd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private void initView(){
        idEdit = findViewById(R.id.edit_id);
        psswrdEdit = findViewById(R.id.edit_password);
        commitBtn = findViewById(R.id.login_btn);
    }

    private void initAction(){
        id = idEdit.getText().toString();
        psswrd = psswrdEdit.getText().toString();
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }
}
