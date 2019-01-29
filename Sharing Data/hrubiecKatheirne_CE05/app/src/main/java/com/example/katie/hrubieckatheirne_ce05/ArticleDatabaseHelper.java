package com.example.katie.hrubieckatheirne_ce05;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class ArticleDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATA_TABLE = "articles";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            ArticleDataContract.DATA_TABLE + " (" +
            ArticleDataContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ArticleDataContract.TITLE + " TEXT, " +
            ArticleDataContract.THUMBNAIL + " TEXT, "
            + ArticleDataContract.BODY + " TEXT)";

    public ArticleDatabaseHelper (Context context){
        super(context,DATA_TABLE,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {db.execSQL(CREATE_TABLE);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
