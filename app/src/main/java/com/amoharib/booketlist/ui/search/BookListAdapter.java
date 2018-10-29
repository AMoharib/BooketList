package com.amoharib.booketlist.ui.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amoharib.booketlist.app.data.remote.model.Work;
import com.amoharib.booketlist.R;
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

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookListViewHolder> {

    private PublishSubject<Work> publishSubject = PublishSubject.create();

    public BehaviorSubject<List<Work>> observer = BehaviorSubject.createDefault(Collections.emptyList());
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    BookListAdapter() {

        compositeDisposable.add(observer
                .subscribe(works -> {
                    notifyDataSetChanged();
                }));

    }

    public Observable<Work> getViewClickObservable() {
        return publishSubject;
    }

    @NonNull
    @Override
    public BookListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookListViewHolder holder, int position) {
        holder.populateView(observer.getValue().get(position));
        RxView.clicks(holder.itemView)
                .map(__ -> observer.getValue().get(position))
                .subscribe(publishSubject);
    }

    @Override
    public int getItemCount() {
        return observer.getValue().size();
    }


    class BookListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.book_bg)
        ImageView bookBg;

        @BindView(R.id.book_title)
        TextView title;

        public BookListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void populateView(Work work) {
            title.setText(work.title());
            Picasso.get().load(work.imageUrl()).into(bookBg);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        publishSubject.onComplete();
    }
}
