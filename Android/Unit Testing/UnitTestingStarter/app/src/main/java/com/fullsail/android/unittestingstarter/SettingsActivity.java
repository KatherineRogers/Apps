package com.fullsail.android.unittestingstarter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fullsail.android.unittestingstarter.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            SettingsFragment fragment = SettingsFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, SettingsFragment.TAG)
                    .commit();
        }
    }
}
