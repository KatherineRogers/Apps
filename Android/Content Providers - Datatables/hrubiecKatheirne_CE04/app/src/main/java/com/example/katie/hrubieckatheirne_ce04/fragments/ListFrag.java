package com.example.katie.hrubieckatheirne_ce04.fragments;

import android.app.ListFragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.katie.hrubieckatheirne_ce04.R;
import com.example.katie.hrubieckatheirne_ce04.adapters.CustomListAdapter;
import com.example.katie.hrubieckatheirne_ce04.objects.Contact;

import java.util.ArrayList;

public class ListFrag extends ListFragment {

    private static final String ARG_CONTACTS = "ARG_CONTACTS";
    private SelectedContactListener mListener;

    public static ListFrag newInstance(ArrayList<Contact> contacts) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACTS, contacts);
        ListFrag fragment = new ListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    public interface SelectedContactListener {
        void selectedContact(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectedContactListener) {
            mListener = (SelectedContactListener) context;
        } else {
            throw new IllegalArgumentException(getString(R.string.wrong_listener));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return inflater.inflate(R.layout.list_frag, container, false);
        } else {
            return null;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Contact> contacts = (ArrayList<Contact>) getArguments().getSerializable(ARG_CONTACTS);
        CustomListAdapter adapter = new CustomListAdapter(getActivity(), contacts);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mListener.selectedContact(position);
    }
}
