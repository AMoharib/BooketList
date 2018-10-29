package com.amoharib.booketlist.app.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import dagger.android.support.DaggerFragment;

public abstract class BaseViewLayout extends DaggerFragment {
    private ProgressDialog progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        progressBar = new ProgressDialog(getActivity());
        View v = inflater.inflate(getLayoutResource(), container, false);
        onViewReady(v, savedInstanceState);
        return v;
    }

    protected abstract void onViewReady(View v, Bundle savedInstanceState);

    public abstract int getLayoutResource();


    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showProgress(String message) {
        progressBar.setMessage(message);
        progressBar.show();
    }

    public void hideProgress() {
        progressBar.dismiss();
    }
}
