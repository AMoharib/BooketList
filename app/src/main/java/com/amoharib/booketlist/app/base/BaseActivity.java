package com.amoharib.booketlist.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initDI();
        setContentView(getView());
        onViewReady(savedInstanceState);
    }

    protected abstract int getView();

    protected abstract void onViewReady(Bundle savedInstanceState);

    protected void initDI() {

    }

}
