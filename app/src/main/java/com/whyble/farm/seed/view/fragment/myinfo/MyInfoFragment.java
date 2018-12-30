package com.whyble.farm.seed.view.fragment.myinfo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whyble.farm.seed.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment {


    public MyInfoFragment() {
        // Required empty public constructor
    }

    public static MyInfoFragment newInstance() {
        return new MyInfoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_info, container, false);
    }

}
