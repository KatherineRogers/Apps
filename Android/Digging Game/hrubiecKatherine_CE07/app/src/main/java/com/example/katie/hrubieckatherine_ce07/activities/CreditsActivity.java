package com.example.katie.hrubieckatherine_ce07.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.katie.hrubieckatherine_ce07.R;
import com.example.katie.hrubieckatherine_ce07.fragments.CreditsFragment;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            CreditsFragment fragment = CreditsFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, CreditsFragment.TAG)
                    .commit();
        }
    }
}
