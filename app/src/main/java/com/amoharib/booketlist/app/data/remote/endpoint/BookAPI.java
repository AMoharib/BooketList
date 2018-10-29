package com.amoharib.booketlist.app.data.remote.endpoint;

import com.amoharib.booketlist.app.data.remote.model.BookDescription;
import com.amoharib.booketlist.app.data.remote.model.Results;
import com.amoharib.booketlist.ext.Constants;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookAPI {
    @GET(Constants.SEARCH_URL)
    Observable<Results> getBooks(@Query("q") String query);

    @GET("book/show/{book_id}.xml?key=" + Constants.API_KEY)
    Observable<BookDescription> getBookDescription(@Path("book_id") String bookId);
}
