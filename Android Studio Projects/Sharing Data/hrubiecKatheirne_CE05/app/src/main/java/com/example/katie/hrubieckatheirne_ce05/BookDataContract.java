package com.example.katie.hrubieckatheirne_ce05;

import android.net.Uri;

class BookDataContract {

    private static final String URI_AUTHORITY = "com.fullsail.ce05.provider";
    private static final String CONTENT_URI_STRING = "content://" + URI_AUTHORITY + "/";

    private static final String DATA_TABLE = "books.db";
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING + DATA_TABLE);

    public static final String TITLE = "title";
    public static final String THUMBNAIL = "thumbnail";
    public static final String DESCRIPTION = "Description";
}
