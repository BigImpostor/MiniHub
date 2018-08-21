package com.example.minihub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.jaeger.library.StatusBarUtil;

public class SearchActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mToolbar = findViewById(R.id.searchActivity_Toolbar);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.material_teal_accent_700),50);
    }
}
