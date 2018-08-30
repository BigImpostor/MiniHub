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


import com.example.minihub.activity.CollectionActivity;
import com.example.minihub.R;

import com.example.minihub.activity.WebActivity;
import com.example.minihub.adapter.ProfileAdapter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class ProfileFragment extends Fragment implements ProfileAdapter.OnClickItemListener{

    private static final String Tag = "ProfileFragment";

    private RecyclerView recyclerView;
    private ProfileAdapter mAdapter;
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
        mAdapter = new ProfileAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.setOnClickItemListener(this);
    }

    @Override
    public void showCollection(){
        Log.e("ProfileFragment:","showCollection");
        Intent intent = new Intent(getActivity(), CollectionActivity.class);
        startActivity(intent);
    }

    @Override
    public void exit() {
        getActivity().finish();
    }

    @Override
    public void about() {
        String link = "https://github.com/BigImpostor/WanAndroid";
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("link", link);
        intent.putExtra("title", "Github");
        startActivity(intent);
    }


    private void addDisposable(Disposable disposable){
        if(mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null){
            mCompositeDisposable.clear();
        }

    }
}
