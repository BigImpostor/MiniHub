package com.example.minihub;

import io.reactivex.observers.ResourceObserver;

public abstract class SimplifyObserver<T> extends ResourceObserver<T> {



    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
