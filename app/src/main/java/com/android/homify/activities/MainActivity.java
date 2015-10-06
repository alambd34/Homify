package com.android.homify.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.homify.activities.adapter.PreferenceArrayAdapter;
import com.android.homify.db.PreferencesDaoImpl;
import com.android.homify.model.Preference;
import com.android.homify.model.PreferenceViewHolder;

import java.util.List;

import home.test.com.homilfy.R;

public class MainActivity extends Activity {

    public static final String USER_PREFERENCE = "user-preference";
    private ListView mainListView = null;
    private List<Preference> preferences = null;
    private ArrayAdapter<Preference> listAdapter = null;

    private PreferencesDaoImpl preferencesDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.preferencesDao = new PreferencesDaoImpl(this);
        this.preferencesDao.open(getResources());

        mainListView = (ListView) findViewById(R.id.mainListView);

        // When item is tapped, toggle checked properties of CheckBox and
        // Preference.
        mainListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View item,
                                            int position, long id) {
                        Preference preference = listAdapter.getItem(position);
                        preference.toggleChecked();
                        PreferenceViewHolder viewHolder = (PreferenceViewHolder) item
                                .getTag();
                        viewHolder.getCheckBox().setChecked(preference.isChecked());
                    }
                });

        // Create and populate preferences.
        this.preferences = getSavedPreferences();

        // Set our custom array adapter as the ListView's adapter.
        listAdapter = new PreferenceArrayAdapter(this, this.preferences, preferencesDao);
        mainListView.setAdapter(listAdapter);
    }

    private List<Preference> getSavedPreferences() {

        List<Preference> preferencesByType = this.preferencesDao.getPreferencesByType(USER_PREFERENCE);
        return preferencesByType;
    }


    public Object onRetainNonConfigurationInstance() {
        return preferences;
    }



}

