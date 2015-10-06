package com.android.homify.db;

import com.android.homify.model.Preference;

import java.util.List;

/**
 * Created by nickyska on 9/29/2015.
 */
public interface PreferenceDao {

    List<Preference> getAll();

    List<Preference> getPreferencesByType(String type);

    Preference addPreference(Preference pref);
}
