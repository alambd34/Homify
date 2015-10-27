package com.android.homify.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.homify.R;
import com.android.homify.activities.adapter.PreferenceArrayAdapter;
import com.android.homify.db.PreferencesDaoImpl;
import com.android.homify.model.Preference;
import com.android.homify.model.PreferenceViewHolder;

import java.util.List;

/**
 * Created by nickyska on 10/8/2015.
 */
public abstract class AbstarctPreferenceActivity extends Activity {

    protected PreferencesDaoImpl preferencesDao;
    private ListView mainListView = null;
    private List<Preference> preferences = null;
    private PreferenceArrayAdapter listAdapter = null;
    private SearchView search;
    private View locationView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences_layout);
        // search = (SearchView) findViewById(R.action_bar_user.action_bar_user);

        //enableAddressBar();

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
        this.preferences = getPreferencesList();

        // Set our custom array adapter as the ListView's adapter.
        listAdapter = new PreferenceArrayAdapter(this, this.preferences, preferencesDao);
        mainListView.setAdapter(listAdapter);
    }

    //if from unit preferences screen no address bar should be present
//    protected void enableAddressBar() {
//
//        View addresbarTextView = findViewById(R.id.address_bar_id);
//        switch (getActionBarLayoutId()) {
//            case R.menu.action_bar_user:
//                addresbarTextView.setVisibility(View.GONE);
//            default:
//                addresbarTextView.setEnabled(true);
//        }
//    }

    private void setSearchViewListener(SearchView searchView) {

        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                Toast.makeText(getBaseContext(), String.valueOf(hasFocus),
//                        Toast.LENGTH_SHORT).show();
            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                listAdapter.getFilter().filter(newText);
                return false;
            }
        });


    }

    public View getActionBarView(final Activity activity) {

        //final String packageName = activity instanceof AppCompatActivity ? activity.getPackageName() : "android";
        final String packageName = "android";
        final int resId = activity.getResources().getIdentifier("action_bar_container", "id", packageName);
        final View view = activity.findViewById(resId);
        return view;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(getActionBarLayoutId(), menu);

        this.search = (SearchView) menu.findItem(R.id.action_search).getActionView();

        setSearchViewListener(this.search);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * @returns id of the layout in action bar
     */
    protected abstract int getActionBarLayoutId();

    protected abstract List<Preference> getPreferencesList();


}
