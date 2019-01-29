package com.example.katie.hrubieckatheirne_ce04.objects;

import android.net.Uri;

import java.io.Serializable;

public class Contact implements Serializable {

    private final String firstLast;
    private final String photoUri;
    private final String primaryNum;
    private final String id;

    //all
    public Contact(String firstLast, String photoUri, String primaryNum, String id) {
        this.firstLast = firstLast;
        this.photoUri = photoUri;
        this.primaryNum = primaryNum;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getFirstLast() {
        return firstLast;
    }

    public Uri getPhotoUri() {
        if (photoUri != null) {
            return Uri.parse(photoUri);
        } else {
            return null;
        }
    }

    public String getPrimaryNum() {
        if (primaryNum != null) {
            return primaryNum;
        } else {
            return "";
        }
    }

}
