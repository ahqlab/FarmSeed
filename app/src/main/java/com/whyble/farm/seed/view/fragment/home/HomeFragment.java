package com.whyble.farm.seed.view.fragment.home;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.whyble.farm.seed.MainActivity;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.adapter.AbsractCommonAdapter;
import com.whyble.farm.seed.databinding.BonusSeedListviewItemBinding;
import com.whyble.farm.seed.databinding.CashSeedListviewItemBinding;
import com.whyble.farm.seed.databinding.CommonSeedListviewItemBinding;
import com.whyble.farm.seed.databinding.FarmSeedListviewItemBinding;
import com.whyble.farm.seed.databinding.FragmentHomeBinding;
import com.whyble.farm.seed.databinding.SaveSeedListviewItemBinding;
import com.whyble.farm.seed.domain.CommonSeedHistory;
import com.whyble.farm.seed.domain.Message;
import com.whyble.farm.seed.domain.seeds.bonus.Bonus;
import com.whyble.farm.seed.domain.seeds.cash.Cash;
import com.whyble.farm.seed.domain.seeds.farm.Farm;
import com.whyble.farm.seed.domain.seeds.notice.Notice;
import com.whyble.farm.seed.domain.seeds.save.Save;
import com.whyble.farm.seed.util.ViewUtil;
import com.whyble.farm.seed.view.friend.invite.InviteFriendActivity;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;

    AbsractCommonAdapter<Save> saveSeedAdapter;
    AbsractCommonAdapter<Farm> farmSeedAdapter;
    AbsractCommonAdapter<Cash> cashSeedAdapter;
    AbsractCommonAdapter<Bonus> bonuaSeedAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.videoView.stopPlayback();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.videoView.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setActivity(this);
        binding.boardText.setSelected(true);
        binding.boardText.setHorizontallyScrolling(true);
        startVideo();
        binding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

            @Override
            public void onCompletion(MediaPlayer mp) {
                binding.videoView.start();
            }
        });
        return binding.getRoot();
    }

    public void startVideo(){
        String uriPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(uriPath);
        binding.videoView.setVideoURI(uri);
        binding.videoView.requestFocus();
        binding.videoView.start();
    }

    public void onClickSaveSeedMore(View view) {
        Intent intent = new Intent(getActivity(), SaveSeedActivity.class);
        startActivity(intent);

    }

    public void onClickFarmSeedMore(View view) {
        Intent intent = new Intent(getActivity(), FarmSeedActivity.class);
        startActivity(intent);
    }

    public void onClickMySeedMore(View view) {
        Intent intent = new Intent(getActivity(), MySeedActivity.class);
        startActivity(intent);
    }

    public void onClickBonusSeedMore(View view) {
        Intent intent = new Intent(getActivity(), BonusSeedActivity.class);
        startActivity(intent);
    }
    public void onClickInco1(View view) {
        Intent intent = new Intent(getActivity(), SaveSeedActivity.class);
        startActivity(intent);
    }
    public void onClickInco2(View view) {
        Intent intent = new Intent(getActivity(), InviteFriendActivity.class);
        startActivity(intent);
    }

    public void setSaveSeedList(List<Save> list) {
        saveSeedAdapter = new AbsractCommonAdapter<Save>(getActivity(), list) {

            SaveSeedListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = saveSeedAdapter.inflater.inflate(R.layout.save_seed_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(saveSeedAdapter.data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (SaveSeedListviewItemBinding) convertView.getTag();
                    adapterBinding.setDomain(saveSeedAdapter.data.get(position));
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
        binding.saveSeedListView.setAdapter(saveSeedAdapter);
        ViewUtil.setListViewHeightBasedOnChildren(binding.saveSeedListView);
    }

    public void setFarmSeedList(List<Farm> list) {
        farmSeedAdapter = new AbsractCommonAdapter<Farm>(getActivity(), list) {

            FarmSeedListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = farmSeedAdapter.inflater.inflate(R.layout.farm_seed_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(farmSeedAdapter.data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (FarmSeedListviewItemBinding) convertView.getTag();
                    adapterBinding.setDomain(farmSeedAdapter.data.get(position));
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
        binding.farmSeedListView.setAdapter(farmSeedAdapter);
        ViewUtil.setListViewHeightBasedOnChildren(binding.farmSeedListView);
    }

    public void setCashSeedList(List<Cash> list) {
        cashSeedAdapter = new AbsractCommonAdapter<Cash>(getActivity(), list) {

            CashSeedListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = cashSeedAdapter.inflater.inflate(R.layout.cash_seed_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(cashSeedAdapter.data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (CashSeedListviewItemBinding) convertView.getTag();
                    adapterBinding.setDomain(cashSeedAdapter.data.get(position));
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
        binding.mySeedListView.setAdapter(cashSeedAdapter);
        ViewUtil.setListViewHeightBasedOnChildren(binding.mySeedListView);
    }

    public void setBonusSeedList(List<Bonus> list) {
        bonuaSeedAdapter = new AbsractCommonAdapter<Bonus>(getActivity(), list) {

            BonusSeedListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = farmSeedAdapter.inflater.inflate(R.layout.bonus_seed_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(bonuaSeedAdapter.data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (BonusSeedListviewItemBinding) convertView.getTag();
                    adapterBinding.setDomain(bonuaSeedAdapter.data.get(position));
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
        binding.bonusSeedListView.setAdapter(bonuaSeedAdapter);
        ViewUtil.setListViewHeightBasedOnChildren(binding.bonusSeedListView);
    }

    public void showSaveSeedEmptyView(boolean show) {
        if (!show) {
            binding.saveSeedListView.setVisibility(View.VISIBLE);
            binding.saveSeedListViewIsEmpty.setVisibility(View.GONE);
        } else {
            binding.saveSeedListView.setVisibility(View.GONE);
            binding.saveSeedListViewIsEmpty.setVisibility(View.VISIBLE);
        }
    }

    public void showFarmSeedEmptyView(boolean show) {
        if (!show) {
            binding.farmSeedListView.setVisibility(View.VISIBLE);
            binding.farmSeedListViewIsEmpty.setVisibility(View.GONE);
        } else {
            binding.farmSeedListView.setVisibility(View.GONE);
            binding.farmSeedListViewIsEmpty.setVisibility(View.VISIBLE);
        }
    }

    public void showCashSeedEmptyView(boolean show) {
        if (!show) {
            binding.mySeedListView.setVisibility(View.VISIBLE);
            binding.mySeedListViewIsEmpty.setVisibility(View.GONE);
        } else {
            binding.mySeedListView.setVisibility(View.GONE);
            binding.mySeedListViewIsEmpty.setVisibility(View.VISIBLE);
        }
    }

    public void showBonusSeedEmptyView(boolean show) {
        if (!show) {
            binding.bonusSeedListView.setVisibility(View.VISIBLE);
            binding.bonusSeedListViewIsEmpty.setVisibility(View.GONE);
        } else {
            binding.bonusSeedListView.setVisibility(View.GONE);
            binding.bonusSeedListViewIsEmpty.setVisibility(View.VISIBLE);
        }
    }

    public void setBoardContent(List<Notice> notice) {

        StringBuffer sb = new StringBuffer();
        for (Notice noti: notice) {
            sb.append(noti.getTitle());
            sb.append("                                ");
            sb.append("                                ");
        }
        binding.boardText.setText(sb.toString());
        binding.boardText.setSelected(true);

    }

    public void setMainMessage(List<Message> message) {
        StringBuffer sb = new StringBuffer();
        for (Message ms: message) {
            if(ms.getTitle() != null){
                sb.append(ms.getTitle());
            }
        }
        binding.message.setText(sb.toString());
    }
}
