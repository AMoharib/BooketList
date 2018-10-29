package com.amoharib.booketlist.ui.search;


import com.amoharib.booketlist.app.base.BasePresenter;
import com.amoharib.booketlist.app.base.BaseView;
import com.amoharib.booketlist.app.data.remote.model.Results;
import com.amoharib.booketlist.app.data.remote.model.Work;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import io.reactivex.Observable;

public interface SearchContract {
    interface View extends BaseView<Presenter> {

        void showResults(Results results);

        boolean hideResults();

        Observable<SearchViewQueryTextEvent> observeSearchBtn();

        void showMessage(String message);

        void showProgress(String message);

        void hideProgress();

        Observable<Work> observeBooksList();

        void goToBookDetails(Work work);

        void onDestroy();
    }

    interface Presenter extends BasePresenter<View> {

        void subscribe(View view);

        void unsubscribe();
    }
}
