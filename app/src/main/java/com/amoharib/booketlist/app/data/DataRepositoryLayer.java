package com.amoharib.booketlist.app.data;

import com.amoharib.booketlist.app.data.local.Book;
import com.amoharib.booketlist.app.data.remote.model.BookDescription;
import com.amoharib.booketlist.app.data.remote.model.Results;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DataRepositoryLayer implements DataRepository.Remote, DataRepository.Local {

    private DataRepository.Remote remote;
    private DataRepository.Local local;

    public DataRepositoryLayer(DataRepository.Remote remote, DataRepository.Local local) {
        this.remote = remote;
        this.local = local;
    }


    //region remote

    @Override
    public Observable<Results> getResults(String query) {
        return remote.getResults(query);
    }

    @Override
    public Observable<BookDescription> getBookDescription(String bookId) {
        return remote.getBookDescription(bookId);
    }

    @Override
    public void login(String email, String password, Action success, Consumer<String> error) {
        remote.login(email, password, success, error);
    }

    @Override
    public void register(String email, String password, Action success, Consumer<String> error) {
        remote.register(email, password, success, error);
    }

    @Override
    public void logout() {
        remote.logout();
    }

    @Override
    public boolean isLoggedIn() {
        return remote.isLoggedIn();
    }

    @Override
    public void getAllBooksRemotely(Consumer<List<Book>> books) {
        remote.getAllBooksRemotely(books);
    }

    @Override
    public void saveBookRemotely(Book book, Action success, Consumer<String> error) {
        remote.saveBookRemotely(book, success, error);
    }

    @Override
    public void deleteBookRemotely(String bookId, Action success, Consumer<String> error) {
        remote.deleteBookRemotely(bookId, success, error);
    }

    @Override
    public void updateBooks(List<Book> books) {
        remote.updateBooks(books);
    }

    //endregion

    //region local

    @Override
    public Single<List<Book>> getBooks() {
        return local.getBooks();
    }

    @Override
    public Single<Book> getBookWithId(String id) {
        return local.getBookWithId(id);
    }

    @Override
    public void insertBook(Book book) {
        Completable.fromAction(() -> local.insertBook(book))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public int deleteBookWithId(String id) {
        Completable.fromAction(() -> local.deleteBookWithId(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        return 0;
    }

    @Override
    public void updateBook(Book book, int progress) {
        Completable.fromAction(() -> local.updateBook(book, progress))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void checkIfThereIsALongTimeUnreadBooks(Consumer<List<Book>> found) {
        local.checkIfThereIsALongTimeUnreadBooks(found);
    }

    @Override
    public List<Book> getBooksSync() {
        return local.getBooksSync();
    }

    @Override
    public void saveBooksLocally(List<Book> books) {
        Completable.fromAction(() -> local.saveBooksLocally(books))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }



    //endregion

    public void syncBooks() {
        List<Book> books = getBooks().blockingGet();
        updateBooks(books);
    }
}
