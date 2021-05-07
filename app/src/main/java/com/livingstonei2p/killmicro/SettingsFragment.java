package com.livingstonei2p.killmicro;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      //  setPreferencesFromResource(R.xml.root_preferences, rootKey);
        addPreferencesFromResource(R.xml.root_preferences);
    }


}