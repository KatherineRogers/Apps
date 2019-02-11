// Katherine Hrubiec
// 1808
// CE04 - Contact Data


package com.example.katie.hrubieckatheirne_ce04.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.katie.hrubieckatheirne_ce04.R;
import com.example.katie.hrubieckatheirne_ce04.fragments.DetailsFrag;
import com.example.katie.hrubieckatheirne_ce04.fragments.ListFrag;
import com.example.katie.hrubieckatheirne_ce04.objects.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListFrag.SelectedContactListener {

    private final ArrayList<Contact> contacts = new ArrayList<>();
    private Contact contact = null;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 0x01001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragments();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, R.string.premission_denied_warning, Toast.LENGTH_SHORT).show();
            } else {
                loadFragments();
            }
        }
    }

    private void getContacts() {
        ContentResolver resolver = getContentResolver();
        Cursor nameCur = resolver.query(ContactsContract.Data.CONTENT_URI, null, ContactsContract.Data.MIMETYPE + " = ?",
                new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE}, ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        ArrayList<String> phoneNumbers = new ArrayList<>();
        if (nameCur != null && cursor != null) {
            while (nameCur.moveToNext() && cursor.moveToNext()) {
                String first = nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                String last = nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                String uri = nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                if (phoneCursor != null) {
                    while (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNumbers.add(phoneNumber);
                    }
                    phoneCursor.close();
                    String primaryNum = null;
                    if (!phoneNumbers.isEmpty()) {
                        primaryNum = phoneNumbers.get(0);
                    }
                    if (first != null && last != null) {
                        contacts.add(new Contact(first + " " + last, uri, primaryNum, id));
                    } else if (first != null) {
                        contacts.add(new Contact(first, uri, primaryNum, id));
                    } else if (last != null) {
                        contacts.add(new Contact(last, uri, primaryNum, id));
                    }
                    phoneNumbers.clear();
                }
            }
            nameCur.close();
            cursor.close();
        }
    }


    private void loadFragments() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                getContacts();
                if (!contacts.isEmpty()) {
                    contact = contacts.get(0);
                }
                getFragmentManager().beginTransaction().replace(R.id.list, ListFrag.newInstance(contacts)).commit();
                getFragmentManager().beginTransaction().replace(R.id.details, DetailsFrag.newInstance(contact)).commit();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        loadFragments();
    }

    @Override
    public void selectedContact(int position) {
        contact = contacts.get(position);
        getFragmentManager().beginTransaction().replace(R.id.details, DetailsFrag.newInstance(contact)).commit();
    }
}
