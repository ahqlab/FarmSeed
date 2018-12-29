package com.whyble.farm.seed.view.myfarm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whyble.farm.seed.R;
import com.whyble.farm.seed.view.home.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFarmFragment extends Fragment {


    public MyFarmFragment() {
        // Required empty public constructor
    }


    public static MyFarmFragment newInstance() {
        return new MyFarmFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_farm, container, false);
    }

}
