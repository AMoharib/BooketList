package com.amoharib.booketlist.app.data.remote.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

@AutoValue
@Xml
public abstract class BookDescription implements Parcelable {

    @Path("book")
    @PropertyElement(name = "description")
    public abstract String description();

    @Path("book")
    @PropertyElement(name = "num_pages")
    public abstract String numberOfPages();
}
