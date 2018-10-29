package com.amoharib.booketlist.ui.details;

import com.amoharib.booketlist.app.data.remote.model.Work;
import com.amoharib.booketlist.app.di.ActivityScope;
import com.amoharib.booketlist.app.di.FragmentScope;
import com.amoharib.booketlist.ext.Constants;

import butterknife.BindFloat;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailsModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract DetailsFragment detailsFragment();

    @ActivityScope
    @Binds
    abstract DetailsContract.Presenter presenter(DetailsPresenter presenter);

    @ActivityScope
    @Provides
    static Work work(DetailsActivity activity) {
        return activity.getIntent().getParcelableExtra(Constants.WORK_INTENT_KEY);
    }
}
