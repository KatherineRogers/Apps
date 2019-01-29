package com.example.katie.hrubieckatherine_ce03_sharingimages;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ImageGridFrag.GetImageListener {

    private final ArrayList<String> imageFilePaths = new ArrayList<>();
    private static final int REQUEST_TAKE_PICTURE = 0x01001;
    private String filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFiles();
        setGrid();
    }

    private void getCurrentTime() {
        filepath = String.valueOf(System.currentTimeMillis());
    }

    private void setGrid() {
        getFragmentManager().beginTransaction().replace(R.id.frame, ImageGridFrag.newInstance(imageFilePaths)).commit();
    }

    private void getFiles() {
        File protectedStorage = getExternalFilesDir(getString(R.string.images_folder));
        if (protectedStorage != null) {
            File[] files = protectedStorage.listFiles();
            for (File f : files) {
                imageFilePaths.add(f.getAbsolutePath());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED && resultCode == RESULT_OK) {
            //save to images array and reload gridview
            imageFilePaths.add(getOutputFilePath());
            setGrid();
        }
    }

    private Uri getOutputUri() {
        //get the URI to return to another application
        getCurrentTime();
        File protectedStorageLocation = getExternalFilesDir(getString(R.string.images_folder));
        File imageFile = new File(protectedStorageLocation, filepath + getString(R.string.jpg));
        try {
            if (imageFile.createNewFile()) {
                return FileProvider.getUriForFile(this, getString(R.string.authority), imageFile);
            } else {
                throw new IllegalArgumentException(getString(R.string.cannot_create_new_file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileProvider.getUriForFile(this, getString(R.string.authority), imageFile);
    }

    private String getOutputFilePath() {
        //get a patch to save an image to
        File protectedStorageLocation = getExternalFilesDir(getString(R.string.images_folder));
        File imageFile = new File(protectedStorageLocation, filepath + getString(R.string.jpg));
        try {
            if (!imageFile.createNewFile()) {
                return imageFile.getAbsolutePath();
            } else {
                throw new IllegalArgumentException(getString(R.string.no_new_file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile.getAbsolutePath();
    }

    @Override
    public void getImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputUri());
        startActivityForResult(intent, REQUEST_TAKE_PICTURE);
    }

    @Override
    public void selectImage(int position) {
        File imageFile = new File(imageFilePaths.get(position));
        Uri uri = FileProvider.getUriForFile(this, "com.fullsail.android.fileprovider", imageFile);
        Intent getIntent = getIntent();
        if (getIntent.getAction() != null) {
            if (!getIntent.getAction().equals(Intent.ACTION_PICK)) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "image/*");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                setResult(RESULT_OK, intent);
                startActivity(intent);
            } else {
                //send image back to app
                Intent intent = getIntent();
                if (intent.getAction() != null) {
                    if (intent.getAction().equals(Intent.ACTION_PICK)) {
                        Intent pickIntent = new Intent();
                        pickIntent.getExtras();
                        pickIntent.setData(uri);
                        pickIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        setResult(RESULT_OK, pickIntent);
                        finish();
                    }
                }
            }
        } else {
            throw new IllegalArgumentException(getString(R.string.intent_null));
        }
    }
}
