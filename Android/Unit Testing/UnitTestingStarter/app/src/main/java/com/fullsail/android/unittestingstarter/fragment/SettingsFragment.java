package com.fullsail.android.unittestingstarter.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.fullsail.android.unittestingstarter.R;

public class SettingsFragment extends PreferenceFragment {

    public static final String TAG = "SettingsFragment.TAG";

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.format_prefs_screen);
    }
}
