package com.whyble.farm.seed.view.seed.list.bonus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.adapter.AbsractCommonAdapter;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityBonusSeedBinding;
import com.whyble.farm.seed.databinding.AllBonusSeedListviewItemBinding;
import com.whyble.farm.seed.databinding.AllFarmSeedListviewItemBinding;
import com.whyble.farm.seed.domain.seeds.bonus.all.Bonus;
import com.whyble.farm.seed.domain.seeds.bonus.all.BonusList;
import com.whyble.farm.seed.domain.seeds.farm.all.Farm;
import com.whyble.farm.seed.domain.seeds.farm.all.FarmList;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;

import java.util.List;

public class BonusSeedActivity extends BaseActivity<BonusSeedActivity> implements BonusIn.View {


    ActivityBonusSeedBinding binding;

    BonusIn.Presenter presenter;

    AbsractCommonAdapter<Bonus> bonusSeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bonus_seed);
        presenter = new BonusSeedPresenter(BonusSeedActivity.this);
        presenter.loadData(BonusSeedActivity.this);
        presenter.getSeeds();
        binding.toolbar.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }

    @Override
    protected BaseActivity<BonusSeedActivity> getActivityClass() {
        return BonusSeedActivity.this;
    }

    @Override
    public void getSeedResult(String s) {
        Gson gson = new Gson();
        BonusList response = gson.fromJson(s, BonusList.class);
        if(response.getBouns_list() != null){
            setSaveSeedList(response.getBouns_list());
            setTotalSeed(response.getBonus());
        }
    }

    private void setTotalSeed(String seed) {
        binding.totalSeed.setText("전체 Seed : " + seed);
    }

    public void setSaveSeedList(List<Bonus> list) {

        bonusSeedAdapter = new AbsractCommonAdapter<Bonus>(BonusSeedActivity.this, list) {

            AllBonusSeedListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = bonusSeedAdapter.inflater.inflate(R.layout.all_bonus_seed_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(bonusSeedAdapter.data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (AllBonusSeedListviewItemBinding) convertView.getTag();
                    adapterBinding.setDomain(bonusSeedAdapter.data.get(position));
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
        binding.seedListview.setAdapter(bonusSeedAdapter);
    }
}
