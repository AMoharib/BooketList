package com.amoharib.booketlist.app.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = Book.class, version = 1)
public abstract class BooketListDatabase extends RoomDatabase {
    public abstract BookDao bookDao();

    @Override
    public void clearAllTables() {

    }
}
