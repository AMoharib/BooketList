package com.amoharib.booketlist.app.data.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "books")
public final class Book implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "page_count")
    private String page_count;

    @ColumnInfo(name = "current_page")
    private String current_page;

    @ColumnInfo(name = "last_update")
    private long last_update;

    @Ignore
    public Book() {
    }


    public Book(@NonNull String id, String title, String image, String author, String description, String page_count, String current_page, long last_update) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.author = author;
        this.description = description;
        this.page_count = page_count;
        this.current_page = current_page;
        this.last_update = last_update;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPage_count() {
        return page_count;
    }

    public String getCurrent_page() {
        return current_page;
    }

    public long getLast_update() {
        return last_update;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.image);
        dest.writeString(this.author);
        dest.writeString(this.description);
        dest.writeString(this.page_count);
        dest.writeString(this.current_page);
    }

    protected Book(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.image = in.readString();
        this.author = in.readString();
        this.description = in.readString();
        this.page_count = in.readString();
        this.current_page = in.readString();
        this.last_update = in.readLong();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
