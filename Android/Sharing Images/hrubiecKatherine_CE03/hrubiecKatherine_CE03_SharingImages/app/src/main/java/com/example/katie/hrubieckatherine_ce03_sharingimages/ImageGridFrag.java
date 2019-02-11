package com.example.katie.hrubieckatherine_ce03_sharingimages;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageGridFrag extends Fragment {

    private static final String ARG_IMAGES = "ARG_IMAGES";
    private GetImageListener mListener;

    public static ImageGridFrag newInstance(ArrayList<String> images) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_IMAGES, images);

        ImageGridFrag fragment = new ImageGridFrag();
        fragment.setArguments(args);
        return fragment;
    }

    public interface GetImageListener {
        void getImage();
        void selectImage(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GetImageListener) {
            mListener = (GetImageListener) context;
        } else {
            throw new IllegalArgumentException(getString(R.string.not_getimagelistener));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> images = (ArrayList<String>) getArguments().getSerializable(ARG_IMAGES);

        if (getView() != null) {
            GridView gv = getView().findViewById(R.id.gridView);
            TextView tv = getView().findViewById(R.id.takePic);
            gv.setAdapter(new CustomGridView(getView().getContext(), images));
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mListener.selectImage(position);
                }
            });

            if (images != null) {
                if (images.size() == 0) {
                    tv.setVisibility(View.VISIBLE);
                    gv.setVisibility(View.GONE);
                } else {
                    tv.setVisibility(View.GONE);
                    gv.setVisibility(View.VISIBLE);
                }
            }
        } else {
            throw new NullPointerException(getString(R.string.null_view));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_frag, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.camera) {
            mListener.getImage();
        }
        return super.onOptionsItemSelected(item);
    }

}
