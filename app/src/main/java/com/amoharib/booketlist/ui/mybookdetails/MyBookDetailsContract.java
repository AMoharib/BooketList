package com.amoharib.booketlist.ui.mybookdetails;

import com.amoharib.booketlist.app.base.BasePresenter;
import com.amoharib.booketlist.app.base.BaseView;
import com.amoharib.booketlist.app.data.local.Book;

import io.reactivex.Observable;

public interface MyBookDetailsContract {
    interface View extends BaseView<Presenter> {

        Observable<Book> observeSaveBtn();

        Observable<Book> observeRemoveBtn();

        int getCurrentProgress();

        void showMessage(String s);

        void exitActivity();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
