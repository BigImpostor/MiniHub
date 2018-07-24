package com.example.minihub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class LoginActvity extends AppCompatActivity {

    private EditText idEdit;
    private EditText psswrdEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private void initView(){
        idEdit = findViewById(R.id.edit_id);
        psswrdEdit = findViewById(R.id.edit_password);
    }
}
