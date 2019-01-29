package com.example.katie.hrubieckatheirne_ce05;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

public class ArticleContentProvider extends ContentProvider {

    private static final int TABLE_MATCH = 10;

    private ArticleDatabaseHelper mDatabase;
    private UriMatcher mMatcher;

    public ArticleContentProvider() {
    }

    @Override
    public boolean onCreate() {
        mDatabase = new ArticleDatabaseHelper(getContext());
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(ArticleDataContract.URI_AUTHORITY, ArticleDataContract.DATA_TABLE, TABLE_MATCH);
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.

        int uriType = mMatcher.match(uri);
        if (uriType == TABLE_MATCH) {
            return "vnd.android.cursor.dir/vnd." + ArticleDataContract.URI_AUTHORITY + "." + ArticleDataContract.DATA_TABLE;
        }
        throw new IllegalArgumentException("Check if authority or Datatable is correct");

    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        int uriType = mMatcher.match(uri);
        if (uriType == TABLE_MATCH) {
            return mDatabase.getReadableDatabase().query(ArticleDataContract.DATA_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
        }

        throw new IllegalArgumentException("Check if authority or Datatable is correct");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
