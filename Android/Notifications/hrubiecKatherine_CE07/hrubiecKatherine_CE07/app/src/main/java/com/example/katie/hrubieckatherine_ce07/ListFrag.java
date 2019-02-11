package com.example.katie.hrubieckatherine_ce07;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ListFrag extends ListFragment {

    private static final String ARG_ARTICLES = "ARG_ARTICLES";
    private SelectedArticleListener mListener;

    public static ListFrag newInstance(ArrayList<Article> articles) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_ARTICLES, articles);
        ListFrag fragment = new ListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    public interface SelectedArticleListener{
        void selectedArticle(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectedArticleListener) {
            mListener = (SelectedArticleListener) context;
        } else {
            throw new IllegalArgumentException("Context is not of kind SelectedArticleListener");
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
            ArrayList<Article> articles = (ArrayList<Article>) getArguments().getSerializable(ARG_ARTICLES);
            CustomListAdapter adapter = new CustomListAdapter(getActivity(), articles);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mListener.selectedArticle(position);
    }
}
