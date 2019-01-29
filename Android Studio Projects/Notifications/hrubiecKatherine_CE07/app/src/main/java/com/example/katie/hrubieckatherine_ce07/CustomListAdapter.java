package com.example.katie.hrubieckatherine_ce07;

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
    private final ArrayList<Article> mArticle;

    public CustomListAdapter(Context _context, ArrayList<Article> _article) {
        mContext = _context;
        mArticle = _article;
    }

    // Returning the number of objects in our collection.
    @Override
    public int getCount() {
        return mArticle.size();
    }

    // Returning Book objects from our collection.
    @Override
    public Article getItem(int _position) {
        return mArticle.get(_position);
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
        Article article = getItem(_position);
        TextView tv = _convertView.findViewById(R.id.description);
        ImageView iv = _convertView.findViewById(R.id.image);
        tv.setText(article.description);
        Picasso.get().load(article.image).into(iv);
        return _convertView;
    }
}
