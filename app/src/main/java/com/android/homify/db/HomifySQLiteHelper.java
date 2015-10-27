package com.android.homify.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.homify.Constants;
import com.android.homify.R;
import com.android.homify.model.Preference;
import com.android.homify.model.PreferenceBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nickyska on 9/29/2015.
 */
public class HomifySQLiteHelper extends SQLiteOpenHelper {

    //PREFERENCES TABLE
    public static final String TABLE_PREFERENCES = "preferences";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CHECKED = "checked";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_PLACE_ID = "place_id";


    //ADDRESS TABLE
    public static final String TABLE_ADDRESS = "address";
    public static final String COLUMN_ADDRESS = "full_address";



    private static final String DATABASE_NAME = "homify.db";
    private static final int DATABASE_VERSION = 4;
//    // Database creation sql statement
//    private static final String DATABASE_CREATE = "create table "
//            + TABLE_PREFERENCES + "(" + COLUMN_ID
//            + " integer primary key autoincrement, " + COLUMN_CODE
//            + " text , " + COLUMN_DESCRIPTION + " text, " + COLUMN_NAME + " text not null,"
//            + COLUMN_CHECKED + " integer, " + COLUMN_TYPE + " text not null)";

    private static final String CREATE_PREFERENCES_TABLE = "create table "
            + TABLE_PREFERENCES + "(" + COLUMN_CODE
            + " text , " + COLUMN_DESCRIPTION + " text, " + COLUMN_NAME + " text not null,"
            + COLUMN_CHECKED + " integer, " + COLUMN_TYPE + " text not null,"
            + COLUMN_PLACE_ID + " integer, PRIMARY KEY ( " + COLUMN_NAME + "," + COLUMN_TYPE + "));";


    private static final String CREATE_ADDRESS_TABLE = "create table "
            + TABLE_ADDRESS + "(" + COLUMN_ADDRESS
            + " text , " + COLUMN_PLACE_ID + " integer, PRIMARY KEY ( " + COLUMN_PLACE_ID + "));";


    private static HomifySQLiteHelper sInstance;
    private static Resources resources;


    private HomifySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized HomifySQLiteHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new HomifySQLiteHelper(context.getApplicationContext());
            resources = context.getResources();
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(CREATE_PREFERENCES_TABLE);
        database.execSQL(CREATE_ADDRESS_TABLE);

        initialize(resources, database);
    }

    private void initialize(Resources resources, SQLiteDatabase database) {

        String[] itemTypes = resources.getStringArray(R.array.itemTypes);
        List<Preference> preferenceList = getHomePreferences(itemTypes);

        for (Preference pref : preferenceList) {
            initializePreferences(pref, database);
        }
    }

    private void initializePreferences(Preference pref, SQLiteDatabase database) {

        ContentValues values = new ContentValues();

        values.put(HomifySQLiteHelper.COLUMN_CHECKED, pref.isChecked());
        values.put(HomifySQLiteHelper.COLUMN_TYPE, pref.getType());
        values.put(HomifySQLiteHelper.COLUMN_DESCRIPTION, pref.getDescription());
        values.put(HomifySQLiteHelper.COLUMN_NAME, pref.getName());

        long insertId = database.insert(HomifySQLiteHelper.TABLE_PREFERENCES, null, values);

    }

    private List<Preference> getHomePreferences(String[] itemNames) {

        List<Preference> list = new ArrayList<Preference>();

        for (String name : itemNames) {
            list.add(new PreferenceBuilder(name, Constants.USER_PREFERENCE_TYPE).setChecked(false).build());
        }

        return list;
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
