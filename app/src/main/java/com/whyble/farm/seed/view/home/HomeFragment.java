package com.whyble.farm.seed.view.home;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.adapter.AbsractCommonAdapter;
import com.whyble.farm.seed.databinding.CommonSeedListviewItemBinding;
import com.whyble.farm.seed.databinding.FragmentHomeBinding;
import com.whyble.farm.seed.domain.CommonSeedHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;

    AbsractCommonAdapter<CommonSeedHistory> adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setActivity(this);

        List<CommonSeedHistory> commonSeedHistoryList = new ArrayList<CommonSeedHistory>();
        commonSeedHistoryList.add(new CommonSeedHistory("10,000"));
        commonSeedHistoryList.add(new CommonSeedHistory("10,000"));
        commonSeedHistoryList.add(new CommonSeedHistory("10,000"));
        setListview(commonSeedHistoryList);

        return binding.getRoot();
    }

    private void setListview(List<CommonSeedHistory> list){
        adapter = new AbsractCommonAdapter<CommonSeedHistory>(getActivity(), list) {

            CommonSeedListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = adapter.inflater.inflate(R.layout.common_seed_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(adapter.data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (CommonSeedListviewItemBinding) convertView.getTag();
                    adapterBinding.setDomain(adapter.data.get(position));
                }
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return false;
                    }
                });
                return adapterBinding.getRoot();
            }
        };
        binding.saveSeedListView.setAdapter(adapter);

    }

}
