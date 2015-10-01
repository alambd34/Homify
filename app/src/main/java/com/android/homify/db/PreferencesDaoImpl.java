package com.android.homify.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.homify.model.Preference;
import com.android.homify.model.PreferenceBuilder;

import java.util.List;

/**
 * Created by nickyska on 9/29/2015.
 */
public class PreferencesDaoImpl implements PreferenceDao {

    private String[] allColumns = { HomifySQLiteHelper.COLUMN_ID,
            HomifySQLiteHelper.COLUMN_CHECKED,HomifySQLiteHelper.COLUMN_CODE,
            HomifySQLiteHelper.COLUMN_DESCRIPTION,HomifySQLiteHelper.COLUMN_NAME };
    // Database fields
    private SQLiteDatabase database;
    private HomifySQLiteHelper dbHelper;

    public PreferencesDaoImpl(Context context) {
        dbHelper = HomifySQLiteHelper.getInstance(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
        //this.dbHelper.onCreate(database);
    }

    public void close() {
        dbHelper.close();
    }

    @Override
    public List<Preference> getAll() {
        return null;
    }

    @Override
    public Preference getPreferenceByType(String type) {
        return null;
    }

    @Override
    public Preference addPreference(Preference pref) {

        ContentValues values = new ContentValues();
        values.put(HomifySQLiteHelper.COLUMN_CHECKED, pref.isChecked());
        //values.put(HomifySQLiteHelper.COLUMN_CODE, pref.getCode());
        values.put(HomifySQLiteHelper.COLUMN_DESCRIPTION, pref.getDescription());
        values.put(HomifySQLiteHelper.COLUMN_NAME, pref.getName());

        long insertId = database.insert(HomifySQLiteHelper.TABLE_PREFERENCES, null, values);
        Cursor cursor = database.query(HomifySQLiteHelper.TABLE_PREFERENCES, allColumns, HomifySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();

        Preference newPreference = cursorToPreference(cursor);
        cursor.close();
        return newPreference;

    }

    /*
    Take the cursor from db and convert it Preference
     */
    private Preference cursorToPreference(Cursor cursor) {

        Preference preference = new PreferenceBuilder(cursor.getString(4)).build();//code + name

        preference.setId(cursor.getLong(0));//_id
        preference.setChecked(cursor.getInt(1) != 0);//checked
        preference.setDescription(cursor.getString(3));//description
        return preference;
    }
}
