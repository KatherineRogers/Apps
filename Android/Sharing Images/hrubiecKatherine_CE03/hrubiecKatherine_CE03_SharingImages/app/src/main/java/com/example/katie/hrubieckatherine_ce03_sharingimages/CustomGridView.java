package com.example.katie.hrubieckatherine_ce03_sharingimages;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

class CustomGridView extends BaseAdapter {

    private static final long ID_CONSTANT = 0x010101010L;
    private final Context mContext;
    private final ArrayList<String> mImages;

    public CustomGridView(Context _context, ArrayList<String> _images) {
        mContext = _context;
        mImages = _images;
    }

    // Returning the number of objects in our collection.
    @Override
    public int getCount() {
        return mImages.size();
    }

    // Returning Book objects from our collection.
    @Override
    public String getItem(int _position) {
        return mImages.get(_position);
    }

    // Adding our constant and position to create unique ID values.
    @Override
    public long getItemId(int _position) {
        return ID_CONSTANT + _position;
    }

    //inflate baseLayout xml
    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        if (_convertView == null) {
            _convertView = LayoutInflater.from(mContext).inflate(R.layout.base_layout, _parent, false);
        }

        String imageFilePath = getItem(_position);
        Bitmap bmp = BitmapFactory.decodeFile(imageFilePath);
        ImageView iv = _convertView.findViewById(R.id.pic);
        iv.setImageBitmap(bmp);
        return _convertView;
    }
}
