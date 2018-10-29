package com.amoharib.booketlist.app.di.modules;

import com.amoharib.booketlist.app.di.ActivityScope;
import com.amoharib.booketlist.app.service.NotificationService;
import com.amoharib.booketlist.ui.details.DetailsActivity;
import com.amoharib.booketlist.ui.details.DetailsModule;
import com.amoharib.booketlist.ui.search.SearchModule;
import com.amoharib.booketlist.ui.MainActivity;
import com.amoharib.booketlist.ui.login_register.LoginRegisterActivity;
import com.amoharib.booketlist.ui.login_register.LoginRegisterModule;
import com.amoharib.booketlist.ui.mybookdetails.MyBookDetailsActivity;
import com.amoharib.booketlist.ui.mybookdetails.MyBookDetailsModule;
import com.amoharib.booketlist.ui.mybooks.MyBooksModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {SearchModule.class, MyBooksModule.class})
    abstract MainActivity mainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = DetailsModule.class)
    abstract DetailsActivity detailsActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = MyBookDetailsModule.class)
    abstract MyBookDetailsActivity myBookDetailsActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = LoginRegisterModule.class)
    abstract LoginRegisterActivity loginRegisterActivity();

    @ContributesAndroidInjector
    abstract NotificationService notificationService();

}
