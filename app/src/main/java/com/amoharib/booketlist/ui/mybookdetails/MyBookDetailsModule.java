package com.amoharib.booketlist.ui.mybookdetails;

import com.amoharib.booketlist.app.di.ActivityScope;
import com.amoharib.booketlist.app.di.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MyBookDetailsModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract MyBookDetailsFragment myBookDetailsFragment();

    @ActivityScope
    @Binds
    abstract MyBookDetailsContract.Presenter presenter(MyBookDetailsPresenter presenter);
}
