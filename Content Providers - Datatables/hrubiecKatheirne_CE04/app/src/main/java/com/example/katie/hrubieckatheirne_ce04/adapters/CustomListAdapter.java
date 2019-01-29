package com.example.katie.hrubieckatheirne_ce04.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katie.hrubieckatheirne_ce04.R;
import com.example.katie.hrubieckatheirne_ce04.objects.Contact;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x010101010L;
    private final Context mContext;
    private final ArrayList<Contact> mContacts;

    public CustomListAdapter(Context _context, ArrayList<Contact> _contacts) {
        mContext = _context;
        mContacts = _contacts;
    }

    // Returning the number of objects in our collection.
    @Override
    public int getCount() {
        return mContacts.size();
    }

    // Returning Book objects from our collection.
    @Override
    public Contact getItem(int _position) {
        return mContacts.get(_position);
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
        Contact contact = getItem(_position);
        ImageView pic = _convertView.findViewById(R.id.pic);
        TextView name = _convertView.findViewById(R.id.name);
        TextView primaryNum = _convertView.findViewById(R.id.primaryNum);

        name.setText(contact.getFirstLast());
        primaryNum.setText(contact.getPrimaryNum());
        if(contact.getPhotoUri() != null){
            pic.setImageURI(contact.getPhotoUri());
        }

        return _convertView;
    }
}
