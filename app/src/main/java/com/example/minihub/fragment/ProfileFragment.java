package com.example.minihub.fragment;


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
import android.widget.Button;


import com.example.minihub.R;

import com.example.minihub.SimplifyObserver;
import com.example.minihub.adapter.ProfileAdapter;
import com.example.minihub.bean.Collection;
import com.example.minihub.net.AppRetrofit;
import com.example.minihub.net.WanAndroidApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ProfileFragment extends Fragment implements ProfileAdapter.OnClickItemListener{

    private static final String Tag = "ProfileFragment";

    private RecyclerView recyclerView;
    private CompositeDisposable mCompositeDisposable;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.profile_recyclerView);
        ProfileAdapter adapter = new ProfileAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void showCollection(){
        Observable<Collection> observable = AppRetrofit.INSTANCE.getRetrofit(getContext())
                                                                .create(WanAndroidApi.class)
                                                                .collection();

        Disposable disposable = observable.subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribeWith(new SimplifyObserver<Collection>(){
                                                @Override
                                                public void onNext(Collection collection) {
                                                    super.onNext(collection);
                                                    Log.e(Tag,collection.getData().getDatas().get(0).getLink());
                                                }

                                            });
        addDisposable(disposable);
    }

    @Override
    public void exit() {

    }

    @Override
    public void about() {

    }


    private void addDisposable(Disposable disposable){
        if(mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);

    }

}
