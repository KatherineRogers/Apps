package com.example.katie.hrubieckatherine_ce07.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.katie.hrubieckatherine_ce07.R;

public class DrawingFragment extends Fragment {

    public static final String TAG = "DrawingFragment.TAG";
    private OpenInventoryListener mListener;
    public static DrawingFragment newInstance() {
        return new DrawingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.drawing_fragment, container, false);
    }

    public interface OpenInventoryListener{
        void openInventory();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OpenInventoryListener) {
            mListener = (OpenInventoryListener) context;
        } else {
            throw new IllegalArgumentException("Context is not of kind OpenInventoryListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //load found menu
        mListener.openInventory();
        return super.onOptionsItemSelected(item);
    }
}
