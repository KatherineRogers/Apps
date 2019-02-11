package com.example.katie.hrubieckatherine_ce03_accesssharedimages;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity implements ImageFrag.GetGalleryListener {

    private Uri selectedImage;
    private static final int REQUEST_GET_PICTURE = 0x01001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setImage();
    }

    private void setImage() {
        getFragmentManager().beginTransaction().replace(R.id.frame, ImageFrag.newInstance(selectedImage)).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery_menu, menu);
        return true;
    }

    @Override
    public void getGallery() {
        //do work on gallery menu item click
        Intent getImages = new Intent();
        getImages.setType(getString(R.string.image_type));
        getImages.setAction(Intent.ACTION_PICK);
        Intent chooser = Intent.createChooser(getImages, getString(R.string.choose_one));
        getImages.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(chooser, REQUEST_GET_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //set imageview
            selectedImage = data.getData();
            setImage();
        }
    }
}
