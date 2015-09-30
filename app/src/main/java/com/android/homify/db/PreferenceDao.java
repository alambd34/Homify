package com.android.homify.db;

import com.android.homify.model.Preference;

import java.util.List;

/**
 * Created by nickyska on 9/29/2015.
 */
public interface PreferenceDao {

    public List<Preference> getAll();

    public Preference getPreferenceByType(String type);
    
    public Preference addPreference(Preference pref);
}
