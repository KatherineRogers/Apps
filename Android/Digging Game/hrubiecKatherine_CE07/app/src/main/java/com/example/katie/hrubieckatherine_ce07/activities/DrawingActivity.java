package com.example.katie.hrubieckatherine_ce07.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.katie.hrubieckatherine_ce07.fragments.DrawingFragment;
import com.example.katie.hrubieckatherine_ce07.R;

public class DrawingActivity extends AppCompatActivity implements DrawingFragment.OpenInventoryListener {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            DrawingFragment fragment = DrawingFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, DrawingFragment.TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void openInventory() {
        Intent startInventory = new Intent(this, InventoryActivity.class);
        startActivity(startInventory);
    }


}
