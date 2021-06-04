package com.example.mastermind.view;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.mastermind.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
