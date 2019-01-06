package com.whyble.farm.seed.view.fragment.myinfo;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whyble.farm.seed.R;
import com.whyble.farm.seed.databinding.FragmentMyInfoBinding;
import com.whyble.farm.seed.user.signup.update.UpdateActivity;
import com.whyble.farm.seed.view.notice.NoticeActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment implements MyInfoIn.View{


    FragmentMyInfoBinding binding;

    MyInfoIn.Presenter presenter;

    public MyInfoFragment() {
        // Required empty public constructor
    }

    public static MyInfoFragment newInstance() {
        return new MyInfoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_info, container, false);
        binding.setActivity(this);
        presenter = new MyInfoPresenter(this);
        presenter.loadData(getActivity());
        return binding.getRoot();
    }

    public void onClickEditUserInfo(View view){
        Intent intent = new Intent(getActivity(), UpdateActivity.class);
        startActivity(intent);
    }

    public void onClickNotice(View view){
        Intent intent = new Intent(getActivity(), NoticeActivity.class);
        startActivity(intent);
    }

}
