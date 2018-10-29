package com.amoharib.booketlist.ui.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;

import com.amoharib.booketlist.app.base.BaseViewLayout;
import com.amoharib.booketlist.app.data.remote.model.Results;
import com.amoharib.booketlist.R;
import com.amoharib.booketlist.app.data.remote.model.Work;
import com.amoharib.booketlist.app.di.ActivityScope;
import com.amoharib.booketlist.ext.Constants;
import com.amoharib.booketlist.ui.details.DetailsActivity;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

@ActivityScope
public class SearchFragment extends BaseViewLayout implements ViewTreeObserver.OnGlobalLayoutListener, SearchContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.cardView)
    CardView cardView;

    private BookListAdapter adapter;


    @Inject
    SearchContract.Presenter presenter;

    @Inject
    public SearchFragment() {

    }

    @Override
    protected void onViewReady(View v, Bundle savedInstanceState) {
        ButterKnife.bind(this, v);
        initializeViews();
        presenter.subscribe(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    //region public methods

    @Override
    public void showResults(Results results) {
        animateSearchViewToTop(() -> {
            recycler.setVisibility(View.VISIBLE);
            adapter.observer.onNext(results.works());
        });
    }

    @Override
    public boolean hideResults() {
        recycler.setVisibility(View.GONE);
        animateSearchViewToCenter();
        return true;
    }

    @Override
    public Observable<SearchViewQueryTextEvent> observeSearchBtn() {
        return RxSearchView.queryTextChangeEvents(searchView);
    }

    @Override
    public Observable<Work> observeBooksList() {
        return adapter.getViewClickObservable();
    }

    @Override
    public void goToBookDetails(Work work) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(Constants.WORK_INTENT_KEY, work);
        startActivity(intent);
    }

    //endregion

    //region private methods

    private void initializeViews() {
        adapter = new BookListAdapter();
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycler.setAdapter(adapter);
    }

    private void animateSearchViewToTop(Action onAnimationEnd) {

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(cardView, "y", 8);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setDuration(200);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                try {
                    onAnimationEnd.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        objectAnimator.start();

        cardView.getViewTreeObserver().addOnGlobalLayoutListener(this);

    }

    private void animateSearchViewToCenter() {
        cardView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(cardView, "y", displayMetrics.heightPixels / 2 - cardView.getHeight());
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setDuration(200);
        objectAnimator.start();
    }

    //endregion

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    public void onGlobalLayout() {
        cardView.setY(8);
    }
}
