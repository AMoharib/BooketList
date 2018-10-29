package com.amoharib.booketlist.ui.details;

import com.amoharib.booketlist.app.data.DataRepositoryLayer;
import com.amoharib.booketlist.app.data.local.Book;
import com.amoharib.booketlist.app.data.remote.model.BookDescription;
import com.amoharib.booketlist.app.data.remote.model.Work;
import com.amoharib.booketlist.ext.BookStatus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailsPresenter implements DetailsContract.Presenter {

    @Inject
    DataRepositoryLayer dataRepository;

    private DetailsContract.View view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Work work;
    private BookDescription bookDescription;
    private BookStatus bookStatus;

    @Inject
    public DetailsPresenter(Work work) {
        this.work = work;
    }

    @Override
    public void subscribe(DetailsContract.View view) {
        this.view = view;
        compositeDisposable.add(checkBookStatus());
        compositeDisposable.add(getBookDescription(work.id()));
        compositeDisposable.add(observeAddToListBtn());
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private Disposable checkBookStatus() {
        return dataRepository.getBookWithId(work.id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::checkSuccess, this::checkError);
    }

    private void checkError(Throwable throwable) {
        bookStatus = BookStatus.NOT_IN_THE_LIST;
        view.updateBookDetails(work);
        view.updateAddToListBtn(bookStatus);
    }

    private void checkSuccess(Book book) {
        bookStatus = BookStatus.IN_THE_LIST;
        view.updateBookDetails(work);
        view.updateAddToListBtn(bookStatus);
    }

    private Disposable observeAddToListBtn() {
        return view.observeAddToListBtn()
                .map(__ -> mapConverter())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(book -> {
                    switch (bookStatus) {
                        case IN_THE_LIST:
                            dataRepository.deleteBookRemotely(book.getId()
                                    , () -> {
                                        dataRepository.deleteBookWithId(book.getId());
                                        bookStatus = BookStatus.NOT_IN_THE_LIST;
                                        view.updateAddToListBtn(bookStatus);
                                        view.showMessage("Book Deleted Successfully");
                                    }, error -> view.showMessage(error));
                            break;
                        case NOT_IN_THE_LIST:
                            dataRepository.saveBookRemotely(book
                                    , () -> {
                                        dataRepository.insertBook(book);
                                        bookStatus = BookStatus.IN_THE_LIST;
                                        view.updateAddToListBtn(bookStatus);
                                        view.showMessage("Book Add Successfully");
                                    }, error -> view.showMessage(error));
                            break;
                    }
                });
    }

    private Book mapConverter() {
        return new Book(work.id(),
                work.title(),
                work.imageUrl(),
                work.authorName(),
                bookDescription.description(),
                bookDescription.numberOfPages(),
                "0",
                System.currentTimeMillis());
    }

    private Disposable getBookDescription(String bookId) {
        return dataRepository.getBookDescription(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(book -> this.bookDescription = book)
                .map(description -> description.description().replaceAll("<br />", ""))
                .subscribe(description -> {
                    view.showDescription(description);
                });
    }
}
