package com.example.katie.hrubieckatherine_ce07.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.katie.hrubieckatherine_ce07.R;
import com.example.katie.hrubieckatherine_ce07.fragments.InventoryFragment;

public class InventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            InventoryFragment fragment = InventoryFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, InventoryFragment.TAG)
                    .commit();
        }
    }
}
