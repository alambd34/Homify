package com.android.homify.activities;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.homify.R;
import com.android.homify.model.Preference;
import com.android.homify.model.PreferenceBuilder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickyska on 10/8/2015.
 */
public class UnitPreferenceActivity extends AbstarctPreferenceActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // LogCat tag
    private static final String TAG = UnitPreferenceActivity.class.getSimpleName();
    // Location updates intervals in sec
    private final static int UPDATE_INTERVAL = 10000; // 10 sec
    private final static int FATEST_INTERVAL = 5000; // 5 sec
    private final static int DISPLACEMENT = 10; // 10 meters
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;

    @Override
    protected int getActionBarLayoutId() {
        return R.menu.action_bar_unit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View actionBarView = getActionBarView(this);
        actionBarView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayLocation();
            }
        });

    }


    @Override
    public List<Preference> getPreferencesList() {

        String[] itemNames = getApplicationContext().getResources().getStringArray(R.array.itemTypes);
        List<Preference> list = new ArrayList<Preference>();

        for (String name : itemNames) {
            list.add(new PreferenceBuilder(name, UserPreferencesActivity.USER_PREFERENCE).setChecked(false).build());
        }

        return list;
    }


    /**
     * Method to display the location on UI
     */
    private void displayLocation() {

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            //lblLocation.setText(latitude + ", " + longitude);
            Toast.makeText(getBaseContext(), latitude + ", " + longitude, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getBaseContext(), "(Couldn't get the location. Make sure location is enabled on the device)", Toast.LENGTH_SHORT).show();
//            lblLocation
//                    .setText();
        }
    }

    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_location_found:
//
                if (checkPlayServices()) {

                    // Building the GoogleApi client
                    buildGoogleApiClient();
                }

                // Show location button click listener


            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
