package com.amoharib.booketlist.ui.mybooks;

import com.amoharib.booketlist.app.di.ActivityScope;
import com.amoharib.booketlist.app.di.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MyBooksModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract MyBooksFragment myBooksFragment();

    @ActivityScope
    @Binds
    abstract MyBooksContract.Presenter presenter(MyBooksPresenter presenter);
}
