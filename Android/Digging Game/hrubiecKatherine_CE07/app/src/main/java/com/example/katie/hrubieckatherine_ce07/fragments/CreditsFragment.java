package com.example.katie.hrubieckatherine_ce07.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.katie.hrubieckatherine_ce07.R;

public class CreditsFragment extends Fragment {

    public static final String TAG = "CreditsFragment.TAG";

    public static CreditsFragment newInstance() {

        Bundle args = new Bundle();

        CreditsFragment fragment = new CreditsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.credits_fragment, container, false);
    }
}

