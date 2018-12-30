package com.whyble.farm.seed.view.fragment.bioblock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whyble.farm.seed.R;


public class BioBlockFragment extends Fragment {


    public BioBlockFragment() {
        // Required empty public constructor
    }


    public static BioBlockFragment newInstance() {
        return new BioBlockFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bio_block, container, false);
    }
}


