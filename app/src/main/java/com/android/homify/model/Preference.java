package com.android.homify.model;

/**
 * Created by nickyska on 9/29/2015.
 */
public class Preference extends AbstractPreference {

    public Preference(PreferenceBuilder preferenceBuilder) {
        super(preferenceBuilder.getCode(),preferenceBuilder.getDescription(),preferenceBuilder.getName(),preferenceBuilder.getChecked());
    }
}
