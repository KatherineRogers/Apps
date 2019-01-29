package com.example.katie.hrubieckatheirne_ce05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class CustomListAdapter extends BaseAdapter {
    private static final long ID_CONSTANT = 0x010101010L;
    private final Context mContext;
    private final ArrayList<Book> mBooks;

    public CustomListAdapter(Context _context, ArrayList<Book> _people) {
        mContext = _context;
        mBooks = _people;
    }

    // Returning the number of objects in our collection.
    @Override
    public int getCount() {
        return mBooks.size();
    }

    // Returning Book objects from our collection.
    @Override
    public Book getItem(int _position) {
        return mBooks.get(_position);
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
            _convertView = LayoutInflater.from(mContext).inflate(R.layout.base_adapter, _parent, false);
        }
        Book book = getItem(_position);
        TextView tv = _convertView.findViewById(R.id.title);
        ImageView iv = _convertView.findViewById(R.id.image);
        tv.setText(book.title);
        Picasso.get().load(book.thumbnail).into(iv);
        return _convertView;
    }
}


