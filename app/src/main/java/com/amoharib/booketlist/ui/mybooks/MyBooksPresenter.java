package com.amoharib.booketlist.ui.mybooks;

import com.amoharib.booketlist.app.data.DataRepository;
import com.amoharib.booketlist.app.data.DataRepositoryLayer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyBooksPresenter implements MyBooksContract.Presenter {

    private MyBooksContract.View view;

    @Inject
    DataRepositoryLayer model;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public MyBooksPresenter() {

    }

    @Override
    public void subscribe(MyBooksContract.View view) {
        this.view = view;
        getDataOnline();
        compositeDisposable.add(observeOnItemClicked());
    }

    private void getDataOnline() {
        model.getAllBooksRemotely(books -> {
            if (!books.isEmpty()) {
                model.saveBooksLocally(books);
                refreshDatabase();
            }
        });
    }

    private Disposable observeOnItemClicked() {
        return view.observeOnItemClicked()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(book -> view.goToBookDetails(book));
    }

    @Override
    public Disposable getSavedBooks() {
        return model.getBooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(books -> view.showBooks(books));
    }

    @Override
    public void refreshDatabase() {
        compositeDisposable.add(getSavedBooks());
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
