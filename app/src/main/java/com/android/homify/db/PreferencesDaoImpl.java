package com.android.homify.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.homify.model.Preference;
import com.android.homify.model.PreferenceBuilder;
import com.android.homify.model.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nickyska on 9/29/2015.
 */
public class PreferencesDaoImpl implements PreferenceDao {

    private String[] allColumns = {HomifySQLiteHelper.COLUMN_TYPE,//0
            HomifySQLiteHelper.COLUMN_CHECKED, HomifySQLiteHelper.COLUMN_CODE,//1,2
            HomifySQLiteHelper.COLUMN_DESCRIPTION, HomifySQLiteHelper.COLUMN_NAME};//3,4

    private String[] allColumnsAddress = {HomifySQLiteHelper.COLUMN_ADDRESS,//0
            HomifySQLiteHelper.COLUMN_PLACE_ID};//1
    // Database fields
    private SQLiteDatabase database;
    private HomifySQLiteHelper dbHelper;

    public PreferencesDaoImpl(Context context) {
        dbHelper = HomifySQLiteHelper.getInstance(context);
    }

    public void open(Resources resources) {
        database = dbHelper.getWritableDatabase();

    }
    public void close() {
        dbHelper.close();
    }

    @Override
    public List<Preference> getAll() {
        return null;
    }

    @Override
    public List<Preference> getPreferencesByType(String type) {

        Cursor cursor = database.query(HomifySQLiteHelper.TABLE_PREFERENCES, allColumns, HomifySQLiteHelper.COLUMN_TYPE + " = ?", new String[]{type},
                null, null, HomifySQLiteHelper.COLUMN_NAME);

        Cursor cursorAddress = database.query(HomifySQLiteHelper.TABLE_ADDRESS, allColumnsAddress, null, null, null, null, null);

        //placeid  to unit
        Map<Integer, Unit> unitsMap = toUnitsMap(cursorAddress);

        List<Preference> preferences = new ArrayList<Preference>();

        for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
            Preference newPreference = cursorToPreference(cursor, unitsMap);
            preferences.add(newPreference);
        }

        cursorAddress.close();
        cursor.close();
        return preferences;
    }

    private Map<Integer, Unit> toUnitsMap(Cursor cursor) {

        Map<Integer, Unit> unitsMap = new HashMap<>();

        for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {

            Unit unit = new Unit();
            unit.setAddress(cursor.getString(0));

            int placeID = cursor.getInt(1);

            unit.setPlaceId(placeID);
            unitsMap.put(placeID, unit);
        }


        return unitsMap;
    }

    @Override
    public Preference addPreference(Preference pref) {

        ContentValues values = new ContentValues();

        values.put(HomifySQLiteHelper.COLUMN_CHECKED, pref.isChecked());
        values.put(HomifySQLiteHelper.COLUMN_TYPE, pref.getType());
        values.put(HomifySQLiteHelper.COLUMN_DESCRIPTION, pref.getDescription());
        values.put(HomifySQLiteHelper.COLUMN_NAME, pref.getName());

        long insertId = database.replace(HomifySQLiteHelper.TABLE_PREFERENCES, null, values);
        Cursor cursor = database.query(HomifySQLiteHelper.TABLE_PREFERENCES, allColumns,
                HomifySQLiteHelper.COLUMN_NAME + " = ? AND " + HomifySQLiteHelper.COLUMN_TYPE + " = ? ", new String[]{pref.getName(), pref.getType()},
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

        Preference preference = new PreferenceBuilder(cursor.getString(4), cursor.getString(0)).build();//name + type

        preference.setId(cursor.getLong(0));//_id
        preference.setChecked(cursor.getInt(1) != 0);//checked
        preference.setDescription(cursor.getString(3));//description
        return preference;
    } /*
    Take the cursor from db and convert it Preference
     */

    private Preference cursorToPreference(Cursor cursor, Map<Integer, Unit> unitsMap) {

        //TODO check if that is from another type?

        Preference preference = cursorToPreference(cursor);

        if (unitsMap != null) {
            preference.setUnit(unitsMap.get(preference.getUnit().getPlaceId()));
        }

        return preference;
    }
}
