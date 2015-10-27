package com.android.homify.activities;

import com.android.homify.Constants;
import com.android.homify.R;
import com.android.homify.model.Preference;

import java.util.List;


public class UserPreferencesActivity extends AbstarctPreferenceActivity {


    @Override
    protected int getActionBarLayoutId() {
        return R.menu.action_bar_user;
    }

    @Override
    public List<Preference> getPreferencesList() {

        List<Preference> preferencesByType = this.preferencesDao.getPreferencesByType(Constants.USER_PREFERENCE_TYPE);
        return preferencesByType;
    }
}

