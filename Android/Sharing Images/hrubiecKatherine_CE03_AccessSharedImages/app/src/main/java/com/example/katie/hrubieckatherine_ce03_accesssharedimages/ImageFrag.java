package com.example.katie.hrubieckatherine_ce03_accesssharedimages;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageFrag extends Fragment {

    private GetGalleryListener mListener;
    private static final String ARG_IMAGE = "ARG_IMAGE";

    public static ImageFrag newInstance(Uri image) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_IMAGE, image);

        ImageFrag fragment = new ImageFrag();
        fragment.setArguments(args);
        return fragment;
    }

    public interface GetGalleryListener {
        void getGallery();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GetGalleryListener) {
            mListener = (GetGalleryListener) context;
        } else {
            throw new IllegalArgumentException(getString(R.string.not_getgallerylistener));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image_frag, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //set image view
        Uri image = getArguments().getParcelable(ARG_IMAGE);

        if(getView() != null){
                ImageView iv = getView().findViewById(R.id.image);
                TextView tv = getView().findViewById(R.id.selectImage);
                iv.setImageURI(image);

                if (image == null) {
                    tv.setVisibility(View.VISIBLE);
                    iv.setVisibility(View.GONE);
                } else {
                    tv.setVisibility(View.GONE);
                    iv.setVisibility(View.VISIBLE);
                }
        }else{
            throw new NullPointerException(getString(R.string.null_view));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.gallery) {
            mListener.getGallery();
        }
        return super.onOptionsItemSelected(item);
    }
}
