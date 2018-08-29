package com.example.minihub.fragment;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.minihub.MainActivity;
import com.example.minihub.WebActivity;
import com.example.minihub.adapter.BaseAdapter;
import com.example.minihub.adapter.BaseRecyclerOnScrollerListener;
import com.example.minihub.net.AppRetrofit;
import com.example.minihub.HomeRecyclerViewAdapter;
import com.example.minihub.R;
import com.example.minihub.SimplifyObserver;
import com.example.minihub.bean.Article;
import com.example.minihub.bean.BannerBean;
import com.example.minihub.net.WanAndroidApi;
import com.example.minihub.sqlite.ArticleSql;
import com.example.minihub.sqlite.BannerDbHelper;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeRecyclerViewAdapter mHomeRecyclerViewAdapter;
    private CompositeDisposable mCompositeDisposable;
    private List<Article.Datas> dataList = new ArrayList<>();
    private List<String> imagesList = new ArrayList<>();
    private BannerDbHelper dbHelper;
    private SQLiteDatabase database;

    private static final String BANNER_KEY = "Banner";
    private static final String ARTICLE_KEY = "article";

    private static final String Tag = "HomeFragment";

    private int page = 0;
    private int curPage = 1;
    private int totalPage = 10000; //假设总页面尽量大

    public static HomeFragment newInstance(){
        HomeFragment instance = new HomeFragment();
        return instance;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new BannerDbHelper(getActivity());
        database = dbHelper.getWritableDatabase();
        initView(view);
        loadData();
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.home_recycler_view);
        mHomeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getActivity(),dataList,imagesList);
        recyclerView.setAdapter(mHomeRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(new BaseRecyclerOnScrollerListener() {
            @Override
            public void loadMore() {
                loadData();
            }
        });
    }


    private void loadData(){
        if (curPage == totalPage){
            mHomeRecyclerViewAdapter.loadingState(BaseAdapter.STATE_COMPLETE);
        }else{
            mHomeRecyclerViewAdapter.loadingState(BaseAdapter.STATE_LOADING);
        }
        Observable<BannerBean> bannerObservable = AppRetrofit.INSTANCE.getRetrofit(getContext())
                                                            .create(WanAndroidApi.class)
                                                            .banner();
        Observable<Article> articleObservable = AppRetrofit.INSTANCE.getRetrofit(getContext())
                .create(WanAndroidApi.class)
                .article(page++);

        addSubscribe(Observable.zip(bannerObservable, articleObservable, new BiFunction<BannerBean, Article, HashMap<String,Object>>() {
            @Override
            public HashMap<String, Object> apply(BannerBean bannerBeanResponse, Article articleResponse) throws Exception {
                HashMap<String,Object> map = new HashMap<>();
                map.put(BANNER_KEY,bannerBeanResponse);
                map.put(ARTICLE_KEY,articleResponse);
                return map;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new SimplifyObserver<HashMap<String,Object>>() {
                    @Override
                    public void onNext(HashMap<String, Object> map) {
                        super.onNext(map);
                        BannerBean bannerBean = (BannerBean) map.get(BANNER_KEY);
                        Article article = (Article) map.get(ARTICLE_KEY);
                        List<String> images = new ArrayList<>();
                        for(int i = 0; i < bannerBean.getData().size(); i++){
                            images.add(bannerBean.getData().get(i).getImagepath());
                        }
                        curPage = article.getData().getCurpage();
                        totalPage = article.getData().getPagecount();
                        mHomeRecyclerViewAdapter.addArticleDatas(article.getData().getDatas());
                        mHomeRecyclerViewAdapter.addImagesList(images);
                        mHomeRecyclerViewAdapter.setOnItemClickListener(new HomeRecyclerViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int pos) {
                                Intent intent = new Intent(getActivity(),WebActivity.class);
                                String link = dataList.get(pos).getLink();
                                intent.putExtra("link",link);
                                String title = dataList.get(pos).getTitle();
                                intent.putExtra("title",title);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(HomeFragment.class.getName(),e.toString());
                    }

                }));

    }

    private void saveBannerData(BannerBean bannerBean){
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        for(int i = 0; i< bannerBean.getData().size();i++){
            String imagePath = bannerBean.getData().get(i).getImagepath();
            images.add(imagePath);
            String title = bannerBean.getData().get(i).getTitle();
            titles.add(title);
            String url = bannerBean.getData().get(i).getUrl();
            urls.add(url);
        }
        for(int i = 0; i < images.size(); i++){
            ContentValues values = new ContentValues();
            values.put(BannerDbHelper.IMAGE_PATH, images.get(i));
            values.put(BannerDbHelper.TITLE, titles.get(i));
            values.put(BannerDbHelper.URL, urls.get(i));
            database.insert(BannerDbHelper.TABLE_NAME,null,values);
        }
    }

    private void saveArticleData(List<Article.Datas> dataList){
        for(int i = 0; i < dataList.size(); i++){
            ContentValues values = new ContentValues();
            values.put(ArticleSql.AUTHOR, dataList.get(i).getAuthor());
            values.put(ArticleSql.CHAPTER_NAME, dataList.get(i).getChapterName());
            values.put(ArticleSql.DESC, dataList.get(i).getDesc());
            values.put(ArticleSql.DATE, dataList.get(i).getNiceDate());
            values.put(ArticleSql.LINK, dataList.get(i).getLink());
            database.insert(ArticleSql.ARTICLE_TABLE, null,values);
        }

    }

    private List<Article.Datas>  getArticleData(){
        List<Article.Datas> dataList = new ArrayList<>();
        Cursor cursor = database.query(ArticleSql.ARTICLE_TABLE,
                null,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()){
            String chapterName = cursor.getString(1);
            String desc = cursor.getString(2);
            String author = cursor.getString(3);
            String date = cursor.getString(4);
            String link = cursor.getString(5);
            Article.Datas data = new Article.Datas();
            data.setChapterName(chapterName);
            data.setDesc(desc);
            data.setAuthor(author);
            data.setNiceDate(date);
            data.setLink(link);
            dataList.add(data);
        }
        return dataList;
    }

    private void getBannerData(){
        List<BannerBean.Data> bannerDatas = new ArrayList<>();
        List<String> imagesList = new ArrayList<>();
        Cursor cursor = database.query(BannerDbHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while(cursor.moveToNext()){
            String image = cursor.getString(1);
            String title = cursor.getString(2);
            String url = cursor.getString(3);
            BannerBean.Data data = new BannerBean.Data();
            data.setImagepath(image);
            data.setTitle(title);
            data.setUrl(url);
            bannerDatas.add(data);
        }

        for(int i = 0; i < bannerDatas.size(); i++){
            imagesList.add(bannerDatas.get(i).getImagepath());
        }

    }

    private void deleteData(){
        database.delete(BannerDbHelper.TABLE_NAME,null,null);
        database.delete(ArticleSql.ARTICLE_TABLE, null, null);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
        database.close();
    }


    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }


}
