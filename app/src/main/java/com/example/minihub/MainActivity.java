package com.example.minihub;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import java.io.BufferedOutputStream;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String user =  intent.getStringExtra("user");
        initView();
    }

    private void initView(){
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        BottomNavigationItem homeItem = new BottomNavigationItem("Home",
                ContextCompat.getColor(this,R.color.material_teal_accent_700),R.drawable.ic_home);
        BottomNavigationItem starItem = new BottomNavigationItem("Star",
                ContextCompat.getColor(this,R.color.material_teal_accent_700),R.drawable.ic_menu_star);
        BottomNavigationItem notificationItem = new BottomNavigationItem("Notification",
                ContextCompat.getColor(this,R.color.material_teal_accent_700),R.drawable.ic_menu_notifications);
        BottomNavigationItem personItem = new BottomNavigationItem("Me",
                ContextCompat.getColor(this,R.color.material_teal_accent_700),R.drawable.ic_menu_person);
        bottomNavigationView.addTab(homeItem);
        bottomNavigationView.addTab(starItem);
        bottomNavigationView.addTab(notificationItem);
        bottomNavigationView.addTab(personItem);
        bottomNavigationView.willNotRecreate(true);

        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {

            }
        });
    }

}
