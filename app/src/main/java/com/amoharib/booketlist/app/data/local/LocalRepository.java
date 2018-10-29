package com.amoharib.booketlist.app.data.local;

import com.amoharib.booketlist.app.data.DataRepository;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LocalRepository implements DataRepository.Local {

    private BookDao bookDao;

    public LocalRepository(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Single<List<Book>> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public Single<Book> getBookWithId(String id) {
        return bookDao.getBookWithId(id);
    }

    @Override
    public void insertBook(Book book) {
        bookDao.insertBook(book);
    }

    @Override
    public int deleteBookWithId(String id) {
        return bookDao.deleteBookWithId(id);
    }

    @Override
    public void updateBook(Book book, int progress) {
        bookDao.updateBook(book.getId(), String.valueOf(progress), System.currentTimeMillis());
    }

    @Override
    public void checkIfThereIsALongTimeUnreadBooks(Consumer<List<Book>> found) {
        bookDao.checkIfThereIsALongTimeUnreadBooks(System.currentTimeMillis(), TimeUnit.HOURS.toMillis(1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(found);
    }

    @Override
    public List<Book> getBooksSync() {
        return bookDao.getBooks().blockingGet();
    }

    @Override
    public void saveBooksLocally(List<Book> books) {
        bookDao.insertBooks(books);
    }

}
