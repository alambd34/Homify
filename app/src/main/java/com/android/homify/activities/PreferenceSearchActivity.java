package com.android.homify.activities;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Created by nickyska on 10/7/2015.
 */
public class PreferenceSearchActivity extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    public void onListItemClick(ListView listView, View v, int position, long id) {
        // call detail activity for clicked entry
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query =
                    intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    @Override
    public void startSearch(String initialQuery, boolean selectInitialQuery, Bundle appSearchData, boolean globalSearch) {
        super.startSearch(initialQuery, selectInitialQuery, appSearchData, globalSearch);
    }

    private void doSearch(String queryStr) {
        // get a Cursor, prepare the ListAdapter
        // and set it
    }

}
