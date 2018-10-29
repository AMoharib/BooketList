package com.amoharib.booketlist.ui.details;

import com.amoharib.booketlist.app.base.BasePresenter;
import com.amoharib.booketlist.app.base.BaseView;
import com.amoharib.booketlist.app.data.remote.model.Work;
import com.amoharib.booketlist.ext.BookStatus;

import io.reactivex.Observable;


public interface DetailsContract {
    interface View extends BaseView<Presenter> {

        void updateBookDetails(Work work);

        void showDescription(String description);

        Observable<Object> observeAddToListBtn();

        void showMessage(String message);

        void updateAddToListBtn(BookStatus bookStatus);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
