package com.whyble.farm.seed.view.seed.list.my;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.adapter.AbsractCommonAdapter;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityMySeedBinding;
import com.whyble.farm.seed.databinding.AllCashSeedListviewItemBinding;
import com.whyble.farm.seed.databinding.AllFarmSeedListviewItemBinding;
import com.whyble.farm.seed.domain.seeds.cash.all.Cash;
import com.whyble.farm.seed.domain.seeds.cash.all.CashList;
import com.whyble.farm.seed.domain.seeds.farm.all.Farm;
import com.whyble.farm.seed.domain.seeds.farm.all.FarmList;
import com.whyble.farm.seed.util.ViewUtil;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;

import java.util.List;

public class MySeedActivity extends BaseActivity<MySeedActivity> implements MySeedIn.View {

    ActivityMySeedBinding binding;

    MySeedIn.Presenter presenter;

    AbsractCommonAdapter<Cash> cashSeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_seed);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_seed);
        binding.setActivity(MySeedActivity.this);
        presenter = new MySeedPresenter(MySeedActivity.this);
        presenter.loadData(MySeedActivity.this);
        presenter.getSeeds();
        binding.toolbar.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        presenter.getSeeds();
    }
    @Override
    protected BaseActivity<MySeedActivity> getActivityClass() {
        return MySeedActivity.this;
    }

    public void onClickBackBtn(View view){
        finish();
    }

    @Override
    public void getSeedResult(String s) {
        Gson gson = new Gson();
        CashList response = gson.fromJson(s, CashList.class);
        if(response.getSeed_list() != null){
            setSaveSeedList(response.getSeed_list());
            setTotalSeed(response.getSeed());
        }
    }

    private void setTotalSeed(String seed) {
        binding.totalSeed.setText("전체 Seed : " + seed);
    }

    public void setSaveSeedList(List<Cash> list) {
        cashSeedAdapter = new AbsractCommonAdapter<Cash>(MySeedActivity.this, list) {

            AllCashSeedListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = cashSeedAdapter.inflater.inflate(R.layout.all_cash_seed_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(cashSeedAdapter.data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (AllCashSeedListviewItemBinding) convertView.getTag();
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
        binding.seedListview.setAdapter(cashSeedAdapter);
    }

}
