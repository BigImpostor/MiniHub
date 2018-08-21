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

import com.jaeger.library.StatusBarUtil;

import java.security.Key;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private LinearLayout rootView;
    private Toolbar mToolbar;

    private static final String Tag = "WebActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = findViewById(R.id.webView);
        rootView = findViewById(R.id.root_view);
        mToolbar = findViewById(R.id.webActivity_toolBar);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.material_teal_accent_700),50);
        action();
    }

    private void action(){
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        String title = intent.getStringExtra("title");
        mToolbar.setTitle(title);
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
        rootView.removeView(webView);
        webView.destroy();
        Log.e(Tag,"onDestroy");
    }


}
