package com.amoharib.booketlist.ui.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amoharib.booketlist.R;
import com.amoharib.booketlist.app.base.BaseViewLayout;
import com.amoharib.booketlist.app.di.ActivityScope;
import com.amoharib.booketlist.ui.MainActivity;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@ActivityScope
public class LoginFragment extends BaseViewLayout implements LoginRegisterContract.View {

    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.login_btn)
    Button loginBtn;
    Unbinder unbinder;

    @Inject
    LoginRegisterPresenter presenter;

    @Inject
    public LoginFragment() {
    }

    @Override
    protected void onViewReady(View v, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, v);
        presenter.subscribe(this);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_login;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.unsubscribe();
    }


    @OnClick(R.id.login_btn)
    public void onViewClicked() {
        presenter.login(email.getText().toString(), password.getText().toString());
    }

    @Override
    public void goToHomePage() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void notifyEmailNotValid() {
        email.setError("Invalid Email");
    }

    @Override
    public void notifyPasswordNotValid() {
        password.setError("Password must be 8 digits at least");
    }

    @Override
    public void showProgressDialog() {
        ((LoginRegisterActivity) Objects.requireNonNull(getActivity())).showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        ((LoginRegisterActivity) Objects.requireNonNull(getActivity())).hideProgressDialog();
    }

    @Override
    public void notifyConfirmPasswordNotValid() {

    }
}
