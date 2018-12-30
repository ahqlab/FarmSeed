package com.whyble.farm.seed.view.fragment.myfarm;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whyble.farm.seed.R;
import com.whyble.farm.seed.databinding.FragmentMyFarmBinding;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;
import com.whyble.farm.seed.view.sub.findMerchants.FindMerchantsActivity;
import com.whyble.farm.seed.view.sub.giftHistory.GiftSeedHistoryActivity;
import com.whyble.farm.seed.view.sub.giftSeed.GifiSeedActivity;
import com.whyble.farm.seed.view.sub.merchantUseHistory.MerchantUseHistoryActivity;
import com.whyble.farm.seed.view.sub.neighborBlock.NeighborBlockActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFarmFragment extends Fragment {


    FragmentMyFarmBinding binding;

    public MyFarmFragment() {
    }

    public static MyFarmFragment newInstance() {
        return new MyFarmFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_my_farm, container, false);
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_farm, container, false);
        binding.setActivity(this);
        return binding.getRoot();
    }


    public void onClickSaveSeed(View view){
        Intent intent = new Intent(getActivity(), SaveSeedActivity.class);
        startActivity(intent);
    }
    public void onClickFarmSeed(View view){
        Intent intent = new Intent(getActivity(), FarmSeedActivity.class);
        startActivity(intent);
    }
    public void onClickMySeed(View view){
        Intent intent = new Intent(getActivity(), MySeedActivity.class);
        startActivity(intent);
    }
    //이웃블록
    public void onClickNeighborBlock(View view){
        Intent intent = new Intent(getActivity(), NeighborBlockActivity.class);
        startActivity(intent);
    }
    //Seed 선물하기
    public void onClickGiftSeed(View view){
        Intent intent = new Intent(getActivity(), GifiSeedActivity.class);
        startActivity(intent);
    }
    //선물내역
    public void onClickGiftHistory(View view){
        Intent intent = new Intent(getActivity(), GiftSeedHistoryActivity.class);
        startActivity(intent);
    }
    //가맹점 찾기
    public void onClickFindMerchants(View view){
        Intent intent = new Intent(getActivity(), FindMerchantsActivity.class);
        startActivity(intent);
    }
    //가맹점 이용내역
    public void onClickMerchantUseHistory(View view){
        Intent intent = new Intent(getActivity(), MerchantUseHistoryActivity.class);
        startActivity(intent);
    }
    //가맹점 신청하기
    public void onClickApplyingForMerchant(View view){

    }
}
