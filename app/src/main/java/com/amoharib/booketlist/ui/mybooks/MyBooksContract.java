package com.amoharib.booketlist.ui.mybooks;

import com.amoharib.booketlist.app.base.BasePresenter;
import com.amoharib.booketlist.app.base.BaseView;
import com.amoharib.booketlist.app.data.local.Book;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public interface MyBooksContract {
    interface View extends BaseView<Presenter> {

        void showBooks(List<Book> books);

        Observable<Book> observeOnItemClicked();

        void goToBookDetails(Book book);
    }

    interface Presenter extends BasePresenter<View> {

        Disposable getSavedBooks();

        void refreshDatabase();
    }
}
