package com.example.minihub;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.minihub.fragment.HomeFragment;
import com.example.minihub.fragment.NaviFragment;
import com.example.minihub.fragment.ProfileFragment;
import com.example.minihub.fragment.ProjectFragment;
import com.jaeger.library.StatusBarUtil;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationBar bottomNavigationBar;
    private Toolbar mToolbar;
    private HomeFragment mHomeFragment;
    private NaviFragment mNaviFragment;
    private ProjectFragment mProjectFragment;
    private ProfileFragment mProfileFragment;



    private static final String HOME = "首页";
    private static final String ARTICLE = "文章";
    private static final String PROJECT = "项目";
    private static final String PROFILE = "我";

    private long exitTime = 0;


    private static final String Tag = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.material_teal_accent_700),50);
        initView();
    }


    private void initView(){
        mToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        bottomNavigationBar = findViewById(R.id.bottomBar);
        bottomNavigationBar.setBackgroundColor(getResources().getColor(R.color.material_teal_accent_700));
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home, HOME))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_article, ARTICLE))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_project,PROJECT))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_person,PROFILE))
                .initialise();

        mHomeFragment = HomeFragment.newInstance();
        mNaviFragment = NaviFragment.newInstance();
        mProjectFragment = ProjectFragment.newInstance();
        mProfileFragment = ProfileFragment.newInstance();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){
                    case 0:
                        showFragment(mHomeFragment);
                        break;
                    case 1:
                        showFragment(mNaviFragment);
                        break;
                    case 2:
                        showFragment(mProjectFragment);
                        break;
                    case 3:
                        showFragment(mProfileFragment);
                        break;
                }
            }
            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        bottomNavigationBar.selectTab(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu, menu);
        menu.findItem(R.id.search_btn).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        return true;
    }




    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        //TODO:
//        return super.onOptionsItemSelected(item);
//    }


    private void showFragment(Fragment fragment){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(!fragments.contains(fragment))
            transaction.add(R.id.fragment_container,fragment);
        transaction.show(fragment);
        for(Fragment f : fragments){
            if(f != fragment)
                transaction.hide(f);
        }
        transaction.commit();
    }



    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
//            exit();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//
//    private void exit(){
//        if(System.currentTimeMillis() - exitTime > 2000){
//            Toast.makeText(MainActivity.this, "再按一次退出返回桌面", Toast.LENGTH_SHORT).show();
//            exitTime = System.currentTimeMillis();
//        }else {
//            finish();
//            System.exit(0);
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(Tag,"onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(Tag,"onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(Tag,"onResume");
    }

}
