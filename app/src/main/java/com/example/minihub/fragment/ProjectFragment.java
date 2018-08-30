package com.example.minihub.fragment;

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

import com.example.minihub.R;
import com.example.minihub.SimplifyObserver;
import com.example.minihub.activity.WebActivity;
import com.example.minihub.adapter.ProjectAdapter;
import com.example.minihub.bean.Project;
import com.example.minihub.net.AppRetrofit;
import com.example.minihub.net.WanAndroidApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ProjectFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ProjectAdapter mAdapter;
    private CompositeDisposable mCompositeDisposable;

    private static final String Tag = "ProjectFragment";

    public static ProjectFragment newInstance(){
        ProjectFragment instance = new ProjectFragment();
        return instance;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        loadData();
    }

    private void initView(View view){
        mRecyclerView = view.findViewById(R.id.project_recyclerView);

    }


    private void loadData(){
        Observable<Project> observable = AppRetrofit.INSTANCE.getRetrofit(getContext())
                                            .create(WanAndroidApi.class)
                                            .project();

        Disposable disposable = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new SimplifyObserver<Project>() {
            @Override
            public void onNext(final Project project) {
                super.onNext(project);
                mAdapter = new ProjectAdapter(project.getData().getDatas(),getActivity());
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        Intent intent = new Intent(getContext(),WebActivity.class);
                        String link = project.getData().getDatas().get(pos).getLink();
                        intent.putExtra("link",link);
                        String title = project.getData().getDatas().get(pos).getTitle();
                        intent.putExtra("title",title);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e(ProjectFragment.class.getName(),e.toString());
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });

        addSubscribe(disposable);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCompositeDisposable.clear();
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }


}
