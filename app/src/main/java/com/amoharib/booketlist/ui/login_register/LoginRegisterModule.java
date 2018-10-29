package com.amoharib.booketlist.ui.login_register;

import com.amoharib.booketlist.app.di.ActivityScope;
import com.amoharib.booketlist.app.di.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LoginRegisterModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract LoginFragment loginFragement();

    @FragmentScope
    @ContributesAndroidInjector
    abstract RegisterFragment registerFragment();

    @ActivityScope
    @Binds
    abstract LoginRegisterContract.Presenter presenter(LoginRegisterPresenter presenter);
}
