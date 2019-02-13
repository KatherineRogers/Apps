package com.example.katie.hrubieckatherine_ce07.activities;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.katie.hrubieckatherine_ce07.TreasureItem;
import com.example.katie.hrubieckatherine_ce07.fragments.MainScreenFragment;
import com.example.katie.hrubieckatherine_ce07.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainScreenFragment.MainButtonClickListener {

    public static final ArrayList<TreasureItem> items = new ArrayList<>();
    public static final ArrayList<Point> mPoints = new ArrayList<>();
    public static final ArrayList<TreasureItem> foundItems = new ArrayList<>();
    public static final int FINISHED = 0x01001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            MainScreenFragment fragment = MainScreenFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, MainScreenFragment.TAG)
                    .commit();
        }

        try {
            readCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void credits() {
        Intent creditsIntent = new Intent(this,CreditsActivity.class);
        startActivity(creditsIntent);
    }

    @Override
    public void startGame() {
        Intent drawingIntent = new Intent(this,DrawingActivity.class);
        startActivityForResult(drawingIntent, FINISHED);
    }

    private void readCSV() throws IOException {

        InputStream is = getResources().openRawResource(R.raw.items);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line;
        while ((line = reader.readLine()) != null){
            String[] tokens = line.split(",");
            TreasureItem item = new TreasureItem(tokens[0], Integer.parseInt(tokens[1]), 0,0);
            items.add(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == FINISHED){
            foundItems.clear();
            mPoints.clear();
            try {
                readCSV();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
