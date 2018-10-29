package com.amoharib.booketlist.app.data.remote.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@AutoValue
@Xml
public abstract class Results implements Parcelable {
    @Path("search/results")
    @Element
    public abstract List<Work> works();
}
