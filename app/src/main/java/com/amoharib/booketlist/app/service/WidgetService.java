package com.amoharib.booketlist.app.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.amoharib.booketlist.app.BooketListApplication;
import com.amoharib.booketlist.app.data.DataRepositoryLayer;
import com.amoharib.booketlist.app.di.DaggerApplicationComponent;
import com.amoharib.booketlist.app.widget.WidgetListAdapter;

import javax.inject.Inject;

public class WidgetService extends RemoteViewsService {

    @Inject
    DataRepositoryLayer model;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetListAdapter(this.getApplicationContext(), intent, model);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        (((BooketListApplication) getApplication()).getApplicationComponent()).inject(this);
    }
}
