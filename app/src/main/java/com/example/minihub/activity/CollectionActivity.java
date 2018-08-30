package com.example.minihub.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CollectionActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;
    private ProgressBar mProgressBar;

    private CompositeDisposable mCompositeDisposable;

    private int page = 0;
    private int curPage = 1;
    private int totalPage = 10000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
        loadData();
    }

    private void initView(){
        StatusBarUtil.setColor(this,getResources().getColor(R.color.material_teal_accent_700),50);
        mRecyclerView = findViewById(R.id.collection_list_recyclerView);
        mProgressBar = findViewById(R.id.collection_activity_progressBar);
        mAdapter = new ArticleAdapter(CollectionActivity.this, new ArrayList<Article.Datas>());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(CollectionActivity.this));
        mRecyclerView.addOnScrollListener(new BaseRecyclerOnScrollerListener() {
            @Override
            public void loadMore() {
                loadData();
            }
        });
    }

    private void loadData(){
        if (page > totalPage - 1){
            Toast.makeText(CollectionActivity.this,"加载完毕",Toast.LENGTH_SHORT).show();
            return;
        }
        if (curPage >= totalPage - 1){
            mAdapter.loadingState(BaseAdapter.STATE_COMPLETE);
        }else {
            mAdapter.loadingState(BaseAdapter.STATE_LOADING);
        }
        Observable<Article> observable = AppRetrofit.INSTANCE.getRetrofit(CollectionActivity.this)
                .create(WanAndroidApi.class)
                .collection(page);
        Disposable disposable = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SimplifyObserver<Article>(){
                    @Override
                    public void onNext(Article article) {
                        super.onNext(article);
                        curPage = article.getData().getCurpage();
                        totalPage = article.getData().getPagecount();
                        if (totalPage == 1)
                            mAdapter.loadingState(BaseAdapter.STATE_COMPLETE);
                        mAdapter.addItem(article.getData().getDatas());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
        addDisposable(disposable);
    }


    private void addDisposable(Disposable disposable){
        if (mCompositeDisposable == null)
            mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposable);
    }
}
