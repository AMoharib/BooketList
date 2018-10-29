package com.amoharib.booketlist.app.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.amoharib.booketlist.R;
import com.amoharib.booketlist.app.data.DataRepositoryLayer;
import com.amoharib.booketlist.app.data.local.Book;
import com.amoharib.booketlist.ext.Constants;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WidgetListAdapter implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Intent intent;
    private List<Book> books = new ArrayList<>();

    private DataRepositoryLayer model;

    public WidgetListAdapter(Context context, Intent intent, DataRepositoryLayer model) {

        this.context = context;
        this.model = model;
        this.intent = intent;

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        this.books = model.getBooksSync();
    }

    @Override
    public void onDestroy() {
        books.clear();
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Book book = books.get(i);
        intent.putExtra(Constants.BOOK_KEY_EXTRA, book);
        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.widget_book_item);
        try {
            view.setImageViewBitmap(R.id.cover_image, Picasso.get().load(book.getImage()).get());
            view.setTextViewText(R.id.title, book.getTitle());
            view.setTextViewText(R.id.author, book.getAuthor());
            view.setTextViewText(R.id.progress, String.format("%s (%s/%s)", context.getString(R.string.progress), book.getCurrent_page(), book.getPage_count()));
            view.setProgressBar(R.id.progress_bar, Integer.valueOf(book.getPage_count()), Integer.valueOf(book.getCurrent_page()), false);
            view.setOnClickFillInIntent(R.id.widget_item_container, intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
