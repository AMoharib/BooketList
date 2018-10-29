package com.amoharib.booketlist.app.data.remote.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;


@AutoValue
@Xml(name = "work")
public abstract class Work implements Parcelable {
    @PropertyElement(name = "original_publication_year")
    public abstract String publicationYear();

    @PropertyElement(name = "average_rating")
    public abstract String rating();

    //-Book Info-//

    @Path("best_book")
    @PropertyElement(name = "id")
    public abstract String id();

    @Path("best_book")
    @PropertyElement(name = "title")
    public abstract String title();

    @Path("best_book")
    @PropertyElement(name = "image_url")
    public abstract String imageUrl();

    @Path("best_book")
    @PropertyElement(name = "small_image_url")
    public abstract String smallImageUrl();

    //-Author Info-//

    @Path("best_book/author")
    @PropertyElement(name = "id")
    public abstract String authorId();

    @Path("best_book/author")
    @PropertyElement(name = "name")
    public abstract String authorName();

}
