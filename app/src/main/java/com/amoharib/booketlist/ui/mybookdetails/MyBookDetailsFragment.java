package com.amoharib.booketlist.ui.mybookdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amoharib.booketlist.R;
import com.amoharib.booketlist.app.base.BaseViewLayout;
import com.amoharib.booketlist.app.data.local.Book;
import com.amoharib.booketlist.app.di.ActivityScope;
import com.amoharib.booketlist.ext.Constants;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

@ActivityScope
public class MyBookDetailsFragment extends BaseViewLayout implements MyBookDetailsContract.View {

    private static final String TAG = "MyBookDetailsFragment";

    @Inject
    MyBookDetailsPresenter presenter;

    @BindView(R.id.book_image)
    ImageView bookImage;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.author_tv)
    TextView authorTv;
    @BindView(R.id.description_tv)
    TextView descriptionTv;
    @BindView(R.id.progress)
    TextView progress;
    @BindView(R.id.save_btn)
    Button saveBtn;
    @BindView(R.id.current_page_edittext)
    EditText currentPageEdittext;
    @BindView(R.id.pages_count_textview)
    TextView pagesCountTextview;
    @BindView(R.id.remove_btn)
    ImageButton removeBtn;

    Unbinder unbinder;

    private Book book;

    @Inject
    public MyBookDetailsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            book = getArguments().getParcelable(Constants.BOOK_KEY_EXTRA);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewReady(View v, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, v);
        initView();
        presenter.subscribe(this);
    }

    private void initView() {
        pagesCountTextview.setText(book.getPage_count());
        currentPageEdittext.setText(book.getCurrent_page());
        titleTv.setText(book.getTitle());
        authorTv.setText(book.getAuthor());
        descriptionTv.setText(book.getDescription());
        Picasso.get().load(book.getImage()).into(bookImage);
    }

    @Override
    public Observable<Book> observeSaveBtn() {
        return RxView.clicks(saveBtn).observeOn(AndroidSchedulers.mainThread()).map(__ -> book);
    }

    @Override
    public Observable<Book> observeRemoveBtn() {
        return RxView.clicks(removeBtn).observeOn(AndroidSchedulers.mainThread()).map(__ -> book);
    }

    @Override
    public int getCurrentProgress() {
        return Integer.valueOf(currentPageEdittext.getText().toString());
    }

    @Override
    public void exitActivity() {
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_my_book_details;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
