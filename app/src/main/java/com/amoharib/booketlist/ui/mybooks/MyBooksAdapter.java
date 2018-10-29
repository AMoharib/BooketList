package com.amoharib.booketlist.ui.mybooks;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amoharib.booketlist.R;
import com.amoharib.booketlist.app.data.local.Book;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class MyBooksAdapter extends RecyclerView.Adapter<MyBooksAdapter.ViewHolder> {

    private PublishSubject<Book> viewObserver = PublishSubject.create();
    public BehaviorSubject<List<Book>> books = BehaviorSubject.createDefault(Collections.emptyList());
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MyBooksAdapter() {
        compositeDisposable.add(
                books.subscribe(__ -> notifyDataSetChanged())
        );
    }

    public Observable<Book> getViewClickObservable() {
        return viewObserver;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_book_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = books.getValue().get(position);
        holder.updateUI(book);
        RxView.clicks(holder.itemView)
                .map(__ -> book)
                .subscribe(viewObserver);
    }

    @Override
    public int getItemCount() {
        return books.getValue().size();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        viewObserver.onComplete();
        compositeDisposable.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cover_image)
        ImageView coverImage;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.author)
        TextView author;

        @BindView(R.id.progress)
        TextView progress;

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void updateUI(Book book) {
            Picasso.get().load(book.getImage()).into(coverImage);
            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            progress.setText(String.format("%s (%s/%s)", itemView.getContext().getString(R.string.progress), book.getCurrent_page(), book.getPage_count()));
            progressBar.setProgress((int) (Float.valueOf(book.getCurrent_page()) / Float.valueOf(book.getPage_count()) * 100));
        }
    }
}
