package com.amoharib.booketlist.app.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.amoharib.booketlist.app.data.DataRepository;
import com.amoharib.booketlist.app.data.DataRepositoryLayer;
import com.amoharib.booketlist.app.data.local.BookDao;
import com.amoharib.booketlist.app.data.local.BooketListDatabase;
import com.amoharib.booketlist.app.data.local.LocalRepository;
import com.amoharib.booketlist.app.data.remote.RemoteRepository;
import com.amoharib.booketlist.app.di.ApplicationScope;
import com.amoharib.booketlist.app.data.remote.endpoint.BookAPI;
import com.amoharib.booketlist.ext.Constants;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public class DataModule {

    @ApplicationScope
    @Provides
    TikXml tikXml() {
        return new TikXml.Builder().exceptionOnUnreadXml(false).build();
    }

    @ApplicationScope
    @Provides
    HttpLoggingInterceptor interceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @ApplicationScope
    @Provides
    OkHttpClient client(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @ApplicationScope
    @Provides
    Retrofit retrofit(TikXml tikXml, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(tikXml))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @ApplicationScope
    @Provides
    BookAPI bookAPI(Retrofit retrofit) {
        return retrofit.create(BookAPI.class);
    }

    @ApplicationScope
    @Provides
    DataRepository.Remote networkLayer(BookAPI bookAPI) {
        return new RemoteRepository(bookAPI);
    }


    @ApplicationScope
    @Provides
    DataRepository.Local localLayer(BookDao bookDao) {
        return new LocalRepository(bookDao);
    }

    @ApplicationScope
    @Provides
    BookDao bookDao(BooketListDatabase db) {
        return db.bookDao();
    }

    @ApplicationScope
    @Provides
    BooketListDatabase booketListDatabase(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), BooketListDatabase.class, "Books.db").build();
    }


    @ApplicationScope
    @Provides
    DataRepositoryLayer dataRepositoryLayer(DataRepository.Remote remote, DataRepository.Local local) {
        return new DataRepositoryLayer(remote, local);
    }


}
