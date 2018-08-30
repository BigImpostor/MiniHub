package com.example.minihub.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.minihub.R;
import com.example.minihub.SimplifyObserver;
import com.example.minihub.adapter.ArticleAdapter;
import com.example.minihub.adapter.BaseAdapter;
import com.example.minihub.adapter.BaseRecyclerOnScrollerListener;
import com.example.minihub.bean.Article;
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


public class ArticleListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private List<Article.Datas> articleLists = new ArrayList<>();

    private CompositeDisposable mCompositeDisposable;

    private int curPage = 1;
    private int totalPage = 10000;

    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.material_teal_accent_700),50);
        mRecyclerView = findViewById(R.id.article_list_recyclerView);
        mProgressBar = findViewById(R.id.article_activity_progressBar);
        mToolbar = findViewById(R.id.article_activity_toolBar);
        setSupportActionBar(mToolbar);
        mAdapter = new ArticleAdapter(ArticleListActivity.this, articleLists);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ArticleListActivity.this));
        Intent intent = getIntent();
        final int cid = intent.getIntExtra("id",0);
        String title = intent.getStringExtra("title");
        loadData(cid);
        getSupportActionBar().setTitle(title);
        mRecyclerView.addOnScrollListener(new BaseRecyclerOnScrollerListener() {
            @Override
            public void loadMore() {
                loadData(cid);
            }
        });
    }

    private void loadData(int cid){
        if (page > totalPage - 1){
            Toast.makeText(ArticleListActivity.this, "加载完毕",Toast.LENGTH_SHORT).show();
            return;
        }

        if (curPage == totalPage - 1){
            mAdapter.loadingState(BaseAdapter.STATE_COMPLETE);
        }else{
            mAdapter.loadingState(BaseAdapter.STATE_LOADING);
        }
        Observable<Article> observable = AppRetrofit.INSTANCE.getRetrofit(ArticleListActivity.this)
                .create(WanAndroidApi.class)
                .navigationArticle(page++,cid);

        Disposable disposable = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribeWith(new SimplifyObserver<Article>() {
                    @Override
                    public void onNext(final Article article) {
                        super.onNext(article);
                        curPage = article.getData().getCurpage();
                        totalPage = article.getData().getPagecount();
                        if (totalPage == 1){
                            mAdapter.loadingState(BaseAdapter.STATE_COMPLETE);
                        }
                        mAdapter.addItem(article.getData().getDatas());
                        mProgressBar.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAdapter.setOnClickItemListener(new ArticleAdapter.OnClickItemListener() {
                            @Override
                            public void onClickItem(int pos) {
                                Intent intent = new Intent(ArticleListActivity.this, WebActivity.class);
                                String link = article.getData().getDatas().get(pos).getLink();
                                String title = article.getData().getDatas().get(pos).getTitle();
                                intent.putExtra("link", link);
                                intent.putExtra("title", title);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(ArticleListActivity.class.getSimpleName(), "onError():" + e.toString());
                    }
                });
        addDisposable(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    private void addDisposable(Disposable disposable){
        if (mCompositeDisposable == null)
            mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposable);
    }

}
