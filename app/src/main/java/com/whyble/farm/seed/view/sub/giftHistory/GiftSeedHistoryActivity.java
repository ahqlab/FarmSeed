package com.whyble.farm.seed.view.sub.giftHistory;

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
import com.whyble.farm.seed.databinding.ActivityGiftSeedHistoryBinding;
import com.whyble.farm.seed.databinding.GiftSeedListviewItemBinding;
import com.whyble.farm.seed.databinding.NeighborBlockListviewItemBinding;
import com.whyble.farm.seed.domain.gift.list.Gift;
import com.whyble.farm.seed.domain.gift.list.GiftList;
import com.whyble.farm.seed.domain.gift.list.TotalGift;

import java.util.List;

public class GiftSeedHistoryActivity extends BaseActivity<GiftSeedHistoryActivity> implements GiftSeedHistoryIn.View{

    ActivityGiftSeedHistoryBinding binding;

    GiftSeedHistoryIn.Presenter presenter;

    AbsractCommonAdapter<Gift> giftAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_seed_history);
        binding = DataBindingUtil.setContentView(GiftSeedHistoryActivity.this, R.layout.activity_gift_seed_history);
        binding.setActivity(GiftSeedHistoryActivity.this);
        presenter = new GiftSeedHistoryModelPresenter(GiftSeedHistoryActivity.this);
        presenter.loadData(GiftSeedHistoryActivity.this);
        presenter.getSeeds();
        binding.toolbar.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }

    @Override
    protected BaseActivity<GiftSeedHistoryActivity> getActivityClass() {
        return GiftSeedHistoryActivity.this;
    }

    @Override
    public void getSeedResult(String s) {
        Gson gson = new Gson();
        GiftList response = gson.fromJson(s, GiftList.class);
        setSaveSeedList(response.getGift_list());
        setTotalSeed(response.getPl_mi());
    }

    private void setSaveSeedList(List<Gift> list) {
        giftAdapter = new AbsractCommonAdapter<Gift>(GiftSeedHistoryActivity.this, list) {

            GiftSeedListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = giftAdapter.inflater.inflate(R.layout.gift_seed_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(giftAdapter.data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (GiftSeedListviewItemBinding) convertView.getTag();
                    adapterBinding.setDomain(giftAdapter.data.get(position));
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
        binding.seedListview.setAdapter(giftAdapter);

    }

    private void setTotalSeed(TotalGift totalGift) {
        binding.totalSeed.setText("전체 : " + totalGift.getPlus() + " / " + totalGift.getMinus());
    }
}
