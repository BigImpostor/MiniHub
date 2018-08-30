package com.example.minihub.fragment;

import android.content.Context;
import android.content.Intent;
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

import com.example.minihub.activity.ArticleListActivity;
import com.example.minihub.R;
import com.example.minihub.SimplifyObserver;
import com.example.minihub.adapter.NaviFirstClassAdapter;
import com.example.minihub.adapter.NaviSecondClassAdapter;
import com.example.minihub.bean.Navigation;
import com.example.minihub.net.AppRetrofit;
import com.example.minihub.net.WanAndroidApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class NaviFragment extends Fragment {

    private RecyclerView mFCRecyclerView;
    private RecyclerView mSCRecyclerView;

    private NaviFirstClassAdapter mFCAdapter;
    private NaviSecondClassAdapter mSCAdapter;

    private List<String> firstClassItems = new ArrayList<>();
    private List<Navigation.Children> childrenList = new ArrayList<>();

    private static final String Tag = "NaviFragment";

    private CompositeDisposable mCompositeDisposable;


    private int curPage = 0;
    private int totalPage = 1;

    public static NaviFragment newInstance() {
        NaviFragment fragment = new NaviFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navi, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        loadData();
    }

    private void initView(View view){
        mFCRecyclerView = view.findViewById(R.id.first_class_recyclerView);
        mSCRecyclerView = view.findViewById(R.id.second_class_recyclerView);
        mFCAdapter = new NaviFirstClassAdapter(getContext(), firstClassItems);
        mSCAdapter = new NaviSecondClassAdapter(getContext(), childrenList);
        mFCRecyclerView.setAdapter(mFCAdapter);
        mFCRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSCRecyclerView.setAdapter(mSCAdapter);
        mSCRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void loadData(){

        Observable<Navigation> observable = AppRetrofit.INSTANCE.getRetrofit(getContext())
                .create(WanAndroidApi.class)
                .navigation();

        Disposable disposable = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SimplifyObserver<Navigation>(){
                    @Override
                    public void onNext(final Navigation navigation) {
                        super.onNext(navigation);
                        Log.e(Tag, navigation.getErrorCode() + "");
                        List<Navigation.Data> dataList = navigation.getData();
                        List<String> firstNames = new ArrayList<>();
                        for(Navigation.Data data : dataList){
                            firstNames.add(data.getName());
                        }
                        mFCAdapter.addItems(firstNames);
                        List<Navigation.Children> firstChildren = navigation.getData().get(0).getChildren();
                        mSCAdapter.addItems(firstChildren);
                        mFCAdapter.setOnItemClickListener(new NaviFirstClassAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                List<Navigation.Children> children = navigation.getData().get(position).getChildren();
                                mFCAdapter.selectedItemPosition(position);
                                mSCAdapter.clearItems();
                                mSCAdapter.addItems(children);
                                mFCAdapter.notifyDataSetChanged();
                            }
                        });

                        mSCAdapter.setOnClickItemListener(new NaviSecondClassAdapter.OnClickItemListener() {
                            @Override
                            public void onClickItem(int pos) {
                                Intent intent = new Intent(getActivity(), ArticleListActivity.class);
                                int id = mSCAdapter.getChildrenList().get(pos).getId();
                                String title = mSCAdapter.getChildrenList().get(pos).getName();
                                intent.putExtra("title",title);
                                intent.putExtra("id",id);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(Tag, e.toString());
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
