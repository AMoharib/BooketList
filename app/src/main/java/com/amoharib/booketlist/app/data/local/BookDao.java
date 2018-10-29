package com.amoharib.booketlist.app.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface BookDao {

    @Query("SELECT * FROM Books")
    Single<List<Book>> getBooks();

    @Query("SELECT * FROM Books where id = :id")
    Single<Book> getBookWithId(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(Book book);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertBooks(List<Book> books);

    @Query("DELETE FROM Books WHERE id = :id")
    int deleteBookWithId(String id);

    @Query("UPDATE Books SET current_page = :progress , last_update = :current_time WHERE id = :id")
    void updateBook(String id, String progress, long current_time);

    @Query("SELECT * FROM Books where :currentTime - last_update > :threeDays")
    Single<List<Book>> checkIfThereIsALongTimeUnreadBooks(long currentTime, long threeDays);
}
