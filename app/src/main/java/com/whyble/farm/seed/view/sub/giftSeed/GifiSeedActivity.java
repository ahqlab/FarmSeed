package com.whyble.farm.seed.view.sub.giftSeed;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityGifiSeedBinding;
import com.whyble.farm.seed.util.MathUtil;

public class GifiSeedActivity extends BaseActivity<GifiSeedActivity> implements GifiSeedIn.View{

    ActivityGifiSeedBinding binding;

    GifiSeedIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gifi_seed);
        presenter = new GifiSeedPresenter(GifiSeedActivity.this);
        presenter.loadData(GifiSeedActivity.this);
        presenter.getMyTotlaSeeds();
        binding.toolbar.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }

    @Override
    protected BaseActivity<GifiSeedActivity> getActivityClass() {
        return GifiSeedActivity.this;
    }

    @Override
    public void setMyTotalSeed(String s) {

    }

    @Override
    public void setSavePoint(String save_point) {
        binding.saveSeedPoint.setText(MathUtil.stringToMoneyType(save_point));
    }

    @Override
    public void setFarmPoint(String farm_point) {
        binding.farmSeedPoint.setText(MathUtil.stringToMoneyType(farm_point));
    }

    @Override
    public void setCashPoint(String cash_point) {
        binding.cashSeedPoint.setText(MathUtil.stringToMoneyType(cash_point));
    }
}
