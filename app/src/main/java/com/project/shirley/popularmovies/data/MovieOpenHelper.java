package com.project.shirley.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Shirley Thomas on 4/17/2016.
 */
public class MovieOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "moviesApp";
    private static final String FAVORITES_TABLE_CREATE =
            "CREATE TABLE " + MovieContract.FavoritesEntry.TABLE_NAME + " (" +
                    MovieContract.FavoritesEntry._ID + " INTEGER PRIMARY KEY, " +
                    MovieContract.FavoritesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                    MovieContract.FavoritesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                    MovieContract.FavoritesEntry.COLUMN_VOTE_AVG + " TEXT NOT NULL, " +
                    MovieContract.FavoritesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                    MovieContract.FavoritesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL);";

    MovieOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FAVORITES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MovieOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.FavoritesEntry.TABLE_NAME);
        onCreate(db);
    }
}
