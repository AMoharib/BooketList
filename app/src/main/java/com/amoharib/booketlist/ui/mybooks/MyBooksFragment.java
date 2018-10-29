package com.amoharib.booketlist.ui.mybooks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amoharib.booketlist.R;
import com.amoharib.booketlist.app.base.BaseViewLayout;
import com.amoharib.booketlist.app.data.local.Book;
import com.amoharib.booketlist.app.di.ActivityScope;
import com.amoharib.booketlist.ext.Constants;
import com.amoharib.booketlist.ui.mybookdetails.MyBookDetailsActivity;
import com.amoharib.booketlist.ui.mybookdetails.MyBookDetailsFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

@ActivityScope
public class MyBooksFragment extends BaseViewLayout implements MyBooksContract.View {

    @Inject
    MyBooksContract.Presenter presenter;

    MyBooksAdapter adapter;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Inject
    public MyBooksFragment() {

    }


    @Override
    protected void onViewReady(View v, Bundle savedInstanceState) {
        ButterKnife.bind(this, v);
        initView();
        presenter.subscribe(this);
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyBooksAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_my_books;
    }

    @Override
    public void showBooks(List<Book> books) {
        adapter.books.onNext(books);
    }

    @Override
    public Observable<Book> observeOnItemClicked() {
        return adapter.getViewClickObservable();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.refreshDatabase();
    }

    @Override
    public void goToBookDetails(Book book) {
        Intent intent = new Intent(getActivity(), MyBookDetailsActivity.class);
        intent.putExtra(Constants.BOOK_KEY_EXTRA, book);
        startActivity(intent);
    }
}
