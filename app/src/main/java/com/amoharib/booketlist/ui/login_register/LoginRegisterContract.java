package com.amoharib.booketlist.ui.login_register;

import com.amoharib.booketlist.app.base.BasePresenter;
import com.amoharib.booketlist.app.base.BaseView;

public interface LoginRegisterContract {
    interface View extends BaseView<Presenter> {
        void showMessage(String error);

        void goToHomePage();

        void notifyEmailNotValid();

        void notifyPasswordNotValid();

        void notifyConfirmPasswordNotValid();

        void showProgressDialog();

        void hideProgressDialog();
    }

    interface Presenter extends BasePresenter<View> {

        void login(String email, String password);

        void register(String email, String password, String confirmPassword);
    }
}
