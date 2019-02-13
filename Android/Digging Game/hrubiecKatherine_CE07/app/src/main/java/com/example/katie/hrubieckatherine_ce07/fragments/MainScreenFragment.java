package com.example.katie.hrubieckatherine_ce07.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.katie.hrubieckatherine_ce07.R;

public class MainScreenFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "MainScreenFragment.TAG";
    private MainButtonClickListener mListener;

    public static MainScreenFragment newInstance() {

        Bundle args = new Bundle();

        MainScreenFragment fragment = new MainScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.start){
            mListener.startGame();
        }else if(v.getId() == R.id.credits){
            mListener.credits();
        }
    }

    public interface MainButtonClickListener{
        void credits();
        void startGame();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_screen_fragment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof MainButtonClickListener){
            mListener = (MainButtonClickListener) context;
        }else{
            throw new IllegalArgumentException("Context is not of kind MainButtonClickListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getView() != null) {
            Button start = getView().findViewById(R.id.start);
            start.setOnClickListener(this);
            Button credits = getView().findViewById(R.id.credits);
            credits.setOnClickListener(this);
        }
    }
}
