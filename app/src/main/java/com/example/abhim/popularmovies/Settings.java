package com.example.abhim.popularmovies;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by abhim on 7/10/2016.
 */
public class Settings extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindPreferencesSummaryToValue(findPreference(getString(R.string.action_popular_movies)));
        bindPreferencesSummaryToValue(findPreference(getString(R.string.action_top_rated)));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String stringValue = newValue.toString();

        if (preference instanceof ListPreference) {
            //For list preferences, look up the correct displays value in
            //the preference's 'entries' list (since they have separate labels/values

            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex > 0) {
                preference.setSummary(stringValue);
            } else {
                //For other preferences, set the summary to the value's simple string representation.
                preference.setSummary(stringValue);
            }
        }
        return true;
    }

    public void bindPreferencesSummaryToValue(Preference preference) {

        //Set the listener to watch the Values change
        preference.setOnPreferenceChangeListener(this);

        //Trigger the listener immediately with the preference's
        //Current value
        onPreferenceChange(preference, PreferenceManager
                .getDefaultSharedPreferences(preference.getContext())
                .getString(preference.getKey(), ""));
    }
}
