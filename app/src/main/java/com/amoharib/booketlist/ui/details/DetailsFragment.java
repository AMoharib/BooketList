package com.amoharib.booketlist.ui.details;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amoharib.booketlist.R;
import com.amoharib.booketlist.app.base.BaseViewLayout;
import com.amoharib.booketlist.app.data.remote.model.Work;
import com.amoharib.booketlist.app.di.ActivityScope;
import com.amoharib.booketlist.ext.BookStatus;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

@ActivityScope
public class DetailsFragment extends BaseViewLayout implements DetailsContract.View {

    @Inject
    DetailsContract.Presenter presenter;

    @BindView(R.id.book_image)
    ImageView image;

    @BindView(R.id.title_tv)
    TextView titleTv;

    @BindView(R.id.author_tv)
    TextView authorTv;

    @BindView(R.id.rate_tv)
    TextView rateTv;

    @BindView(R.id.description_tv)
    TextView description;

    @BindView(R.id.btn_add_to_list)
    Button addToListBtn;

    @Inject
    public DetailsFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe(this);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_details;
    }


    private static View vv;
    @Override
    protected void onViewReady(View v, Bundle savedInstanceState) {
        ButterKnife.bind(this, v);

        vv= v.findViewById(R.id.test);
    }


    @Override
    public void updateBookDetails(Work work) {
        Picasso.get().load(work.imageUrl()).into(image);
        titleTv.setText(work.title());
        authorTv.setText(work.authorName());
        rateTv.setText(work.rating());

    }

    @Override
    public void showDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public Observable<Object> observeAddToListBtn() {
        return RxView.clicks(addToListBtn);
    }

    @Override
    public void updateAddToListBtn(BookStatus bookStatus) {
        switch (bookStatus) {
            case NOT_IN_THE_LIST:
                addToListBtn.setText(R.string.add_to_list);
                break;
            case IN_THE_LIST:
                addToListBtn.setText(R.string.remove_from_list);
                break;
        }
    }
}
