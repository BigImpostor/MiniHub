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

    private Banner banner;
    private RecyclerView recyclerView;
    private HomeRecyclerViewAdapter mHomeRecyclerViewAdapter;
    private CompositeDisposable mCompositeDisposable;
    private List<Article.Datas> dataList;
    private BannerDbHelper dbHelper;
    private SQLiteDatabase database;

    private static final String BANNER_KEY = "Banner";
    private static final String ARTICLE_KEY = "article";

    private static final String Tag = "HomeFragment";

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
        banner = view.findViewById(R.id.banner);
        recyclerView = view.findViewById(R.id.home_recycler_view);
        dbHelper = new BannerDbHelper(getActivity());
        database = dbHelper.getWritableDatabase();
        loadData();
    }


    private void loadData(){
        Observable<BannerBean> bannerObservable = AppRetrofit.INSTANCE.getRetrofit(getContext())
                                                            .create(WanAndroidApi.class)
                                                            .banner();
        Observable<Article> articleObservable = AppRetrofit.INSTANCE.getRetrofit(getContext())
                .create(WanAndroidApi.class)
                .article();

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
                        banner.setImageLoader(new GlideImageLoader()).setImages(images).start();
                        dataList = article.getData().getDatas();
                        mHomeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getActivity(),dataList);
                        recyclerView.setAdapter(mHomeRecyclerViewAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mHomeRecyclerViewAdapter.setOnItemClickListener(new HomeRecyclerViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int pos) {
                                Intent intent = new Intent(getActivity(),WebActivity.class);
                                String link = dataList.get(pos).getLink();
                                intent.putExtra("link",link);
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
        banner.setImages(imagesList).setImageLoader(new GlideImageLoader()).start();
    }

    private void deleteData(){
        database.delete(BannerDbHelper.TABLE_NAME,null,null);
        database.delete(ArticleSql.ARTICLE_TABLE, null, null);
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
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


    private class GlideImageLoader extends ImageLoader{

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context).load(path).into(imageView);

        }
    }
}
