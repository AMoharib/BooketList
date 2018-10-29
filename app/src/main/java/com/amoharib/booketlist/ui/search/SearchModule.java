package com.amoharib.booketlist.ui.search;

import com.amoharib.booketlist.app.di.ActivityScope;
import com.amoharib.booketlist.app.di.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SearchModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract SearchFragment homeViewFragment();

    @ActivityScope
    @Binds
    abstract SearchContract.Presenter presenter(SearchPresenter presenter);

}
