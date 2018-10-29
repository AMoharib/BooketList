package com.amoharib.booketlist.app.data;

import com.amoharib.booketlist.app.data.local.Book;
import com.amoharib.booketlist.app.data.remote.model.BookDescription;
import com.amoharib.booketlist.app.data.remote.model.Results;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public interface DataRepository {
    interface Remote {
        Observable<Results> getResults(String query);

        Observable<BookDescription> getBookDescription(String bookId);

        void login(String email, String password, Action success, Consumer<String> error);

        void register(String email, String password, Action success, Consumer<String> error);

        void logout();

        boolean isLoggedIn();

        void getAllBooksRemotely(Consumer<List<Book>> books);

        void saveBookRemotely(Book book, Action success, Consumer<String> error);

        void deleteBookRemotely(String bookId, Action success, Consumer<String> error);

        void updateBooks(List<Book> books);
    }

    interface Local {

        Single<List<Book>> getBooks();

        Single<Book> getBookWithId(String id);

        void insertBook(Book book);

        int deleteBookWithId(String id);

        void updateBook(Book book, int progress);

        void checkIfThereIsALongTimeUnreadBooks(Consumer<List<Book>> found);

        List<Book> getBooksSync();

        void saveBooksLocally(List<Book> books);
    }
}
