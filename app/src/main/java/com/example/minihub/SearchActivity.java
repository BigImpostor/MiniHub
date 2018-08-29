package com.example.minihub;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.minihub.adapter.BaseAdapter;
import com.example.minihub.adapter.BaseRecyclerOnScrollerListener;
import com.example.minihub.adapter.QueryAdapter;
import com.example.minihub.bean.Query;
import com.example.minihub.net.AppRetrofit;
import com.example.minihub.net.WanAndroidApi;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SearchActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Toolbar mToolbar;
    private SearchView searchView;
    private RecyclerView mRecyclerView;
    private QueryAdapter mQueryAdapter;
    private List<Query.Datas> datasList = new ArrayList<>();

    private CompositeDisposable mCompositeDisposable;

    private int page = 0;
    private int curPage = 1;
    private int totalPage = 10000;
    private String queryContent;


    private static final String Tag = "SearchActivity.class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.material_teal_accent_700),50);
        mToolbar = findViewById(R.id.searchActivity_Toolbar);
        setSupportActionBar(mToolbar);
        initView();
    }

    private void initView(){
        mSwipeRefreshLayout = findViewById(R.id.activity_search_swipeRefreshLayout);
        mRecyclerView = findViewById(R.id.search_activity_recyclerView);
        mQueryAdapter = new QueryAdapter(SearchActivity.this,datasList);
        mRecyclerView.setAdapter(mQueryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(queryContent);
            }
        });
        mRecyclerView.addOnScrollListener(new BaseRecyclerOnScrollerListener() {
            @Override
            public void loadMore() {
                loadData(queryContent);
            }
        });
    }


    private void initSearchView(){
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);//默认为true在框内，设置false则在框外
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (datasList != null){
                    datasList.clear();
                    mQueryAdapter.notifyDataSetChanged();
                    page = 0;
                    curPage = 1;
                    totalPage = 1000;
                }
                queryContent = query;
                mSwipeRefreshLayout.setRefreshing(true);
                loadData(query);
                searchView.onActionViewCollapsed();    //可以收起SearchView视图
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_activity_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) menuItem.getActionView();
        initSearchView();
        return super.onCreateOptionsMenu(menu);
    }


    private void loadData(String query){
        if (page > totalPage - 1){
            Toast.makeText(SearchActivity.this, " 加载完毕",Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }

        if (curPage == totalPage - 1){
            mQueryAdapter.loadingState(BaseAdapter.STATE_COMPLETE);
        }else {
            mQueryAdapter.loadingState(BaseAdapter.STATE_LOADING);
        }

        Observable<Query> observable = AppRetrofit.INSTANCE.getRetrofit(SearchActivity.this)
                .create(WanAndroidApi.class)
                .query(page++,query);

        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SimplifyObserver<Query>(){
                    @Override
                    public void onNext(Query query) {
                        if (query.getData().getPageCount() != 0){
                            curPage = query.getData().getCurPage();
                            totalPage = query.getData().getPageCount();
                            if (totalPage == 1)
                                mQueryAdapter.loadingState(BaseAdapter.STATE_COMPLETE);
                            mQueryAdapter.addData(query.getData().getDatas());
                        }else{
                            Toast.makeText(SearchActivity.this,"找不到您需要的东西...",Toast.LENGTH_SHORT).show();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(Tag,"onError():" + e.toString());
                        Toast.makeText(SearchActivity.this,"网络出了点小问题...",Toast.LENGTH_SHORT).show();
                    }
                });

        addDisposable(disposable);
    }

    private void addDisposable(Disposable disposable){
        if (mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

}
