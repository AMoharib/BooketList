package com.amoharib.booketlist.app.di;

import android.app.Application;

import com.amoharib.booketlist.app.BooketListApplication;
import com.amoharib.booketlist.app.data.DataRepository;
import com.amoharib.booketlist.app.data.DataRepositoryLayer;
import com.amoharib.booketlist.app.di.modules.ActivityBindingModule;
import com.amoharib.booketlist.app.di.modules.ApplicationModule;
import com.amoharib.booketlist.app.di.modules.DataModule;
import com.amoharib.booketlist.app.service.WidgetService;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@ApplicationScope
@Component(modules = {ApplicationModule.class,
        DataModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class
})
public interface ApplicationComponent extends AndroidInjector<BooketListApplication> {
    //DataRepositoryLayer networkLayer();
    void inject(WidgetService widgetService);

    //Context context();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}
