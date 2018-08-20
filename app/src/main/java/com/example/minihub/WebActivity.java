package com.example.minihub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import java.security.Key;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private int requestCode;
    private LinearLayout rootView;

    private static final String Tag = "WebActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = findViewById(R.id.webView);
        rootView = findViewById(R.id.root_view);
        action();
    }

    private void action(){
        Intent intent = getIntent();
//        requestCode = intent.getIntExtra("requestCode",0);
        String link = intent.getStringExtra("link");
        webView.loadUrl(link);
    }




    @Override
    protected void onStop() {
        super.onStop();
        Log.e(Tag,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(Tag,"onDestroy");
    }


}
