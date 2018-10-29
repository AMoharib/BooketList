package com.amoharib.booketlist.ui.mybookdetails;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.amoharib.booketlist.R;
import com.amoharib.booketlist.app.base.BaseActivity;
import com.amoharib.booketlist.app.widget.MostUnreadBooksWidget;
import com.amoharib.booketlist.ext.ActivityUtils;

import javax.inject.Inject;

public class MyBookDetailsActivity extends BaseActivity {

    @Inject
    MyBookDetailsFragment fragment;

    @Override
    protected int getView() {
        return R.layout.my_book_details_activity;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        fragment.setArguments(getIntent().getExtras());
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
    }

    @Override
    protected void onStop() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Intent intent2 = new Intent(this, MostUnreadBooksWidget.class);
            intent2.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            int[] ids = AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(getApplicationContext(), MostUnreadBooksWidget.class));
            intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            sendBroadcast(intent2);
        }


        super.onStop();
    }
}
