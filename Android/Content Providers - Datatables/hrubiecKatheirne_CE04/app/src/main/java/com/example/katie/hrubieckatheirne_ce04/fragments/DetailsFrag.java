package com.example.katie.hrubieckatheirne_ce04.fragments;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.katie.hrubieckatheirne_ce04.R;
import com.example.katie.hrubieckatheirne_ce04.objects.Contact;

import java.util.ArrayList;

public class DetailsFrag extends Fragment {

    private static final String ARG_CONTACT = "ARG_CONTACT";


    public static DetailsFrag newInstance(Contact contact) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT, contact);
        DetailsFrag fragment = new DetailsFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return inflater.inflate(R.layout.details_frag, container, false);
        } else {
            return null;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {

            Contact contact = (Contact) getArguments().getSerializable(ARG_CONTACT);
            ArrayList<String> phoneNumbers = new ArrayList<>();
            ContentResolver resolver = getActivity().getContentResolver();
            String id = "";
            if (contact != null) {
                id = contact.getId();
            }
            String name = null;

            Cursor rawContacts = resolver.query(ContactsContract.RawContacts.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
            if (phoneCursor != null && rawContacts != null) {
                while (phoneCursor.moveToNext()) {
                    String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phoneNumbers.add(phoneNumber);
                }
                while (rawContacts.moveToNext()) {
                    name = rawContacts.getString(rawContacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                }
                phoneCursor.close();
                rawContacts.close();

                TextView tv = getView().findViewById(R.id.fullName);
                tv.setText(name);
                TextView noNum = getView().findViewById(R.id.noNums);
                ListView lv = getView().findViewById(R.id.phoneNumbers);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, phoneNumbers);
                lv.setAdapter(arrayAdapter);

                if (contact != null) {
                    if (contact.getPhotoUri() != null) {
                        ImageView iv = getView().findViewById(R.id.largePic);
                        iv.setImageURI(contact.getPhotoUri());
                    }
                    if (phoneNumbers.size() == 0) {
                        noNum.setVisibility(View.VISIBLE);
                        lv.setVisibility(View.GONE);
                    } else {
                        noNum.setVisibility(View.GONE);
                        lv.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

    }
}



