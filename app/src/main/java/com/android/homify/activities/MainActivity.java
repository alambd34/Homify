package com.android.homify.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.homify.activities.adapter.PreferenceArrayAdapter;
import com.android.homify.model.Preference;
import com.android.homify.model.PreferenceBuilder;
import com.android.homify.model.PreferenceViewHolder;

import java.util.ArrayList;
import java.util.List;

import home.test.com.homilfy.R;

public class MainActivity extends Activity {

    private ListView mainListView = null;
    private List<Preference> preferences = null;
    private ArrayAdapter<Preference> listAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
        Object preferences = getLastNonConfigurationInstance();
        if (this.preferences == null) {
            //this.preferences = new Preference[] { new PreferenceBuilder("001","Stam").setChecked(false).build()  };
            String[] itemTypes = getResources().getStringArray(R.array.itemTypes);
            this.preferences = getHomePreferences(itemTypes);
        }
        ArrayList<Preference> preferenceList = new ArrayList<Preference>();
        preferenceList.addAll(this.preferences);

        // Set our custom array adapter as the ListView's adapter.
        listAdapter = new PreferenceArrayAdapter(this, preferenceList);
        mainListView.setAdapter(listAdapter);
    }


    public Object onRetainNonConfigurationInstance() {
        return preferences;
    }

    private List<Preference> getHomePreferences(String[] itemTypes) {

        List<Preference> list = new ArrayList<>();

        for (String type : itemTypes) {
            list.add(new PreferenceBuilder(type).setChecked(false).build());
        }

        return list;
    }

}

