package com.amoharib.booketlist.ui.search;

import com.amoharib.booketlist.app.data.DataRepositoryLayer;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View view;

    @Inject
    DataRepositoryLayer model;

    @Inject
    SearchPresenter() {
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Disposable observeSearchBtn() {
        return view.observeSearchBtn()
                .filter(SearchViewQueryTextEvent::isSubmitted)
                .doOnNext(searchViewQueryTextEvent -> searchViewQueryTextEvent.view().setOnCloseListener(() -> view.hideResults()))
                .doOnNext(__ -> view.showProgress("Loading"))
                .map(SearchViewQueryTextEvent::queryText)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .switchMap(query -> model.getResults(query.toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> view.hideProgress())
                .doOnError(error -> view.showMessage(error.getMessage()))
                .subscribe(results -> {
                            view.showResults(results);
                        }
                        , error -> view.showMessage("No Data Found"));
    }

    private Disposable observeBooksList() {
        return view.observeBooksList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(work -> {
                    view.goToBookDetails(work);
                });
    }


    @Override
    public void subscribe(SearchContract.View view) {
        this.view = view;
        compositeDisposable.add(observeSearchBtn());
        compositeDisposable.add(observeBooksList());
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
