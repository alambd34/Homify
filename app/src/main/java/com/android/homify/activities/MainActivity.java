package com.android.homify.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.homify.R;

/**
 * Created by nickyska on 10/8/2015.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
    }

    public void startUnitPreferenceActivity(View view) {

        Intent i = new Intent(this, UnitPreferenceActivity.class);
        startActivity(i);
        // close this activity
        finish();
    }

    public void startUserreferenceActivity(View view) {
        Intent i = new Intent(this, UserPreferencesActivity.class);
        startActivity(i);
        // close this activity
        finish();

    }

    public void startReviewActivity(View view) {
        Intent i = new Intent(this, PreferencesReviewActivity.class);
        startActivity(i);
        // close this activity
        finish();
    }
}
