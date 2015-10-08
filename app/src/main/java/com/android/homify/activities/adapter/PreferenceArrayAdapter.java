package com.android.homify.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.homify.R;
import com.android.homify.db.PreferencesDaoImpl;
import com.android.homify.model.Preference;
import com.android.homify.model.PreferenceViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nickyska on 9/30/2015.
 */
public class PreferenceArrayAdapter extends ArrayAdapter<Preference> implements Filterable {

    private LayoutInflater inflater;

    private PreferencesDaoImpl preferencesDao;

    private ArrayList<Preference> originalPreferences;
    private ArrayList<Preference> filteredPreferences;
    private Filter filter;

    public PreferenceArrayAdapter(Context context, List<Preference> preferencesList, PreferencesDaoImpl preferencesDao) {
        super(context, R.layout.simplerow, R.id.rowTextView, preferencesList);
        //Cache the LayoutInflate to avoid asking for a new one each time.
        inflater = LayoutInflater.from(context);
        this.preferencesDao = preferencesDao;
        this.originalPreferences = new ArrayList<Preference>(preferencesList);
        this.filteredPreferences = new ArrayList<Preference>(preferencesList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Preference preference = this.getItem(position);
        CheckBox checkBox;
        TextView textView;

        // Create a new row view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simplerow, null);

            textView = (TextView) convertView.findViewById(R.id.rowTextView);
            checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);

            convertView.setTag(new PreferenceViewHolder(textView, checkBox));

            // If CheckBox is toggled, update the planet it is tagged with.
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Preference preference = (Preference) cb.getTag();
                    preference.setChecked(cb.isChecked());
                    preferencesDao.addPreference(preference);
                }
            });
        }
        // Re-use existing row view
        else {

            PreferenceViewHolder viewHolder = (PreferenceViewHolder) convertView.getTag();
            checkBox = viewHolder.getCheckBox();
            textView = viewHolder.getTextView();
        }

        checkBox.setTag(preference);

        // Display planet data
        checkBox.setChecked(preference.isChecked());
        textView.setText(preference.getName());

        return convertView;
    }

    @Override
    public Filter getFilter() {

        if (filter == null)
            filter = new PreferenceFilter();

        return filter;
    }

    private class PreferenceFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();

            if (prefix == null || prefix.length() == 0) {
                ArrayList<Preference> list = new ArrayList<Preference>(originalPreferences);
                results.values = list;
                results.count = list.size();
            } else {
                final ArrayList<Preference> originalListOfPreferences = new ArrayList<Preference>(originalPreferences);
                final ArrayList<Preference> newListOfPreferences = new ArrayList<Preference>();

                for (final Preference pre : originalListOfPreferences) {
                    if (pre.getName().toLowerCase().contains(prefix)) {
                        newListOfPreferences.add(pre);
                    }
                }
                results.values = newListOfPreferences;
                results.count = newListOfPreferences.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filteredPreferences = (ArrayList<Preference>) results.values;

            clear();
            for (Preference pref : filteredPreferences) {
                add(pref);
            }

        }
    }
}
