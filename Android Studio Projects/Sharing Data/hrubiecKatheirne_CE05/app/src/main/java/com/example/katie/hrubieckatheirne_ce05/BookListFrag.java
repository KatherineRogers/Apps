package com.example.katie.hrubieckatheirne_ce05;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class BookListFrag extends ListFragment {

    private SelectedBookListener mListener;
    private static final String ARG_BOOKS = "ARG_BOOKS";

    public static BookListFrag newInstance(ArrayList<Book> books) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOKS, books);
        BookListFrag fragment = new BookListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    public interface SelectedBookListener {
        void selectedBook(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectedBookListener) {
            mListener = (SelectedBookListener) context;
        } else {
            throw new IllegalArgumentException(getString(R.string.not_SelectedBookListener));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_frag, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null){
            ArrayList<Book> books = (ArrayList<Book>) getArguments().getSerializable(ARG_BOOKS);
            CustomListAdapter adapter = new CustomListAdapter(getActivity(), books);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mListener.selectedBook(position);
    }
}
