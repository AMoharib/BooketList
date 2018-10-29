package com.amoharib.booketlist.ui.login_register;

import com.amoharib.booketlist.app.data.DataRepositoryLayer;

import org.apache.commons.validator.routines.EmailValidator;

import javax.inject.Inject;

public class LoginRegisterPresenter implements LoginRegisterContract.Presenter {

    @Inject
    DataRepositoryLayer model;

    private LoginRegisterContract.View view;


    @Inject
    public LoginRegisterPresenter() {

    }

    @Override
    public void subscribe(LoginRegisterContract.View view) {
        this.view = view;
        if (model.isLoggedIn()) view.goToHomePage();
    }

    @Override
    public void unsubscribe() {
    }

    //region login

    @Override
    public void login(String email, String password) {
        view.showProgressDialog();
        if (checkInputs(email, password)) {
            model.login(email, password
                    , () -> {
                        view.goToHomePage();
                        view.hideProgressDialog();
                    }
                    , error -> {
                        view.showMessage(error);
                        view.hideProgressDialog();
                    });
        } else {
            view.hideProgressDialog();
        }
    }

    private boolean checkInputs(String email, String password) {
        if (email.isEmpty() || !EmailValidator.getInstance().isValid(email)) {
            view.notifyEmailNotValid();
            return false;
        }
        if (password.length() < 8) {
            view.notifyPasswordNotValid();
            return false;
        }
        return true;
    }

    //endregion

    //region register

    @Override
    public void register(String email, String password, String confirmPassword) {
        view.showProgressDialog();
        if (checkInputs(email, password, confirmPassword)) {
            model.register(email, password
                    , () -> {
                        view.goToHomePage();
                        view.hideProgressDialog();

                    }
                    , error -> {
                        view.showMessage(error);
                        view.hideProgressDialog();
                    });
        } else {
            view.hideProgressDialog();

        }
    }

    private boolean checkInputs(String email, String password, String confirmPassword) {
        if (!checkInputs(email, password)) {
            return false;
        }
        if (!password.equals(confirmPassword)) {
            view.notifyConfirmPasswordNotValid();
            return false;
        }
        return true;
    }

    //endregion

}
