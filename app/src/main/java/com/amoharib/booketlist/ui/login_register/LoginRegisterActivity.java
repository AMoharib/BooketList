package com.amoharib.booketlist.ui.login_register;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.amoharib.booketlist.R;
import com.amoharib.booketlist.app.base.BaseActivity;
import com.amoharib.booketlist.ext.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lib.kingja.switchbutton.SwitchMultiButton;

public class LoginRegisterActivity extends BaseActivity {

    @Inject
    LoginFragment loginFragment;

    @Inject
    RegisterFragment registerFragment;

    @BindView(R.id.switch_tabs)
    SwitchMultiButton switchMultiButton;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    Unbinder unbinder;

    @Override
    protected int getView() {
        return R.layout.activity_login_register;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this);

        ActivityUtils.addFragmentToActivityWithAnimation(getSupportFragmentManager(), loginFragment, R.id.fragment_container,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);

        switchMultiButton.setOnSwitchListener((position, tabText) -> {
            if (position == 0) {
                ActivityUtils.addFragmentToActivityWithAnimation(getSupportFragmentManager(), loginFragment, R.id.fragment_container,
                        R.animator.fragment_slide_right_enter,
                        R.animator.fragment_slide_right_exit);
            } else if (position == 1) {
                ActivityUtils.addFragmentToActivityWithAnimation(getSupportFragmentManager(), registerFragment, R.id.fragment_container,
                        R.animator.fragment_slide_left_enter,
                        R.animator.fragment_slide_left_exit);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }
}
