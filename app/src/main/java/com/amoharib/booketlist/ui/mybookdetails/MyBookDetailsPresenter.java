package com.amoharib.booketlist.ui.mybookdetails;

import com.amoharib.booketlist.app.data.DataRepositoryLayer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyBookDetailsPresenter implements MyBookDetailsContract.Presenter {

    @Inject
    DataRepositoryLayer model;

    private MyBookDetailsContract.View view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    MyBookDetailsPresenter() {

    }

    @Override
    public void subscribe(MyBookDetailsContract.View view) {
        this.view = view;
        compositeDisposable.add(observeSaveBtn());
        compositeDisposable.add(observeRemoveBtn());
    }

    private Disposable observeRemoveBtn() {
        return view.observeRemoveBtn()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(book -> {
                    model.deleteBookRemotely(book.getId(),
                            () -> {
                                model.deleteBookWithId(book.getId());
                                view.showMessage("Deleted Successfully");
                                view.exitActivity();
                            }, error -> {
                                view.showMessage(error);

                            });
                });
    }

    private Disposable observeSaveBtn() {
        return view.observeSaveBtn()
                .observeOn(Schedulers.io())
                .doOnNext(book -> {
                    int progress = view.getCurrentProgress();
                    model.updateBook(book, progress);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(book -> {
                    view.showMessage("Updated Successfully");
                }, error -> {
                    view.showMessage(error.getLocalizedMessage());
                });
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
