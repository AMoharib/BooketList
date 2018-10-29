package com.amoharib.booketlist.app.base;

public interface BasePresenter<T> {
    void subscribe(T view);

    void unsubscribe();
}
