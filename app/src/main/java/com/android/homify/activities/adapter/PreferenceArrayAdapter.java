package com.android.homify.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.homify.db.PreferencesDaoImpl;
import com.android.homify.model.Preference;
import com.android.homify.model.PreferenceViewHolder;

import java.util.List;

import home.test.com.homilfy.R;

/**
 * Created by nickyska on 9/30/2015.
 */
public class PreferenceArrayAdapter extends ArrayAdapter<Preference> {

    private LayoutInflater inflater;

    private PreferencesDaoImpl preferencesDao;

    public PreferenceArrayAdapter(Context context, List<Preference> planetList) {
        super(context, R.layout.simplerow, R.id.rowTextView, planetList);
        //Cache the LayoutInflate to avoid asking for a new one each time.
        inflater = LayoutInflater.from(context);
        this.preferencesDao = new PreferencesDaoImpl(context);
        this.preferencesDao.open();
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

            PreferenceViewHolder viewHolder = (PreferenceViewHolder) convertView
                    .getTag();
            checkBox = viewHolder.getCheckBox();
            textView = viewHolder.getTextView();
        }

        checkBox.setTag(preference);

        // Display planet data
        checkBox.setChecked(preference.isChecked());
        textView.setText(preference.getName());

        return convertView;
    }

}
