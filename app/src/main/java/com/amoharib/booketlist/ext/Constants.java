package com.amoharib.booketlist.ext;

import com.amoharib.booketlist.BuildConfig;

public class Constants {
    public static final String BASE_URL = "https://www.goodreads.com/";
    public static final String WORK_INTENT_KEY = "WORK_INTENT_KEY";
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String SEARCH_URL = "search/index.xml?key=" + Constants.API_KEY;

    public static final String BOOK_KEY_EXTRA = "BOOK_KEY_EXTRA";

    public static final String SHARED_NAME = "BooketList_Pref";
    public static final String SHARED_FIRST_TIME = "FIRST_TIME";
    public static final String CHANNEL_ID = "CHANNEL_ID";
    public static final String INTENT_FROM_NOTIFICATION_KEY = "INTENT_FROM_NOTIFICATION_KEY";
}
