package com.amoharib.booketlist.ui.details;

import android.os.Bundle;

import com.amoharib.booketlist.R;
import com.amoharib.booketlist.app.base.BaseActivity;
import com.amoharib.booketlist.ext.ActivityUtils;

import javax.inject.Inject;

public class DetailsActivity extends BaseActivity {

    @Inject
    DetailsFragment view;

    @Override
    protected int getView() {
        return R.layout.activity_details;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {

        DetailsFragment fragment = ((DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container));

        if (fragment == null) {
            fragment = view;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

    }
}
