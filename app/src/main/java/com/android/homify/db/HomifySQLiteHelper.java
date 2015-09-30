package com.android.homify.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by nickyska on 9/29/2015.
 */
public class HomifySQLiteHelper extends SQLiteOpenHelper {

    private static HomifySQLiteHelper sInstance;

    public static final String TABLE_PREFERENCES = "preferences";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CHECKED = "checked";

    private static final String DATABASE_NAME = "homify.preferences.db";
    private static final int DATABASE_VERSION = 1;



    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PREFERENCES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_CODE
            + " text not null, " + COLUMN_DESCRIPTION + "text, " +COLUMN_NAME + "text not null,"
            + COLUMN_CHECKED + "integer )";

    public static synchronized HomifySQLiteHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new HomifySQLiteHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private HomifySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(HomifySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCES);
        onCreate(db);
    }

}
