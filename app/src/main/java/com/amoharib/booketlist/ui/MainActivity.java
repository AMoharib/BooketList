package com.amoharib.booketlist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;

import com.amoharib.booketlist.R;
import com.amoharib.booketlist.app.base.BaseActivity;
import com.amoharib.booketlist.app.data.DataRepositoryLayer;
import com.amoharib.booketlist.ext.ActivityUtils;
import com.amoharib.booketlist.ext.Constants;
import com.amoharib.booketlist.ui.login_register.LoginRegisterActivity;
import com.amoharib.booketlist.ui.search.SearchFragment;
import com.amoharib.booketlist.ui.mybooks.MyBooksFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    @Inject
    SearchFragment homeView;

    @Inject
    MyBooksFragment mMyBooksFragment;

    @Inject
    DataRepositoryLayer model;


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), homeView, R.id.fragment_container);
                        return true;
                    case R.id.navigation_dashboard:
                        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mMyBooksFragment, R.id.fragment_container);
                        return true;
                }
                return false;
            };


    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        if (getIntent().getBooleanExtra(Constants.INTENT_FROM_NOTIFICATION_KEY, false)) {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mMyBooksFragment, R.id.fragment_container);
            bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);

        } else {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), homeView, R.id.fragment_container);
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            model.logout();
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
