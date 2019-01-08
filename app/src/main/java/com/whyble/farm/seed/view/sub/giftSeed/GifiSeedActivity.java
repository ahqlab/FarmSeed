package com.whyble.farm.seed.view.sub.giftSeed;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityGifiSeedBinding;
import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.util.MathUtil;
import com.whyble.farm.seed.util.ValidationUtil;

public class GifiSeedActivity extends BaseActivity<GifiSeedActivity> implements GifiSeedIn.View {

    ActivityGifiSeedBinding binding;

    GifiSeedIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gifi_seed);
        binding.setActivity(GifiSeedActivity.this);
        presenter = new GifiSeedPresenter(GifiSeedActivity.this);
        presenter.loadData(GifiSeedActivity.this);
        presenter.getMyTotlaSeeds();
        binding.findUserly.setVisibility(View.GONE);
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

    @Override
    public void setSearchResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        if (response.getResult().matches("1")) {
            binding.notfoundUserly.setVisibility(View.GONE);
            binding.findUserly.setVisibility(View.VISIBLE);
            binding.findUser.setText(binding.searchId.getText().toString());
        } else {
            binding.notfoundUserly.setVisibility(View.VISIBLE);
            binding.findUserly.setVisibility(View.GONE);
            binding.findUser.setText("");
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }


    public void onClickSearchBtn(View view) {
        if (ValidationUtil.isEmptyOfEditText(binding.searchId)) {
            super.showBasicOneBtnPopup(null, getString(R.string.required_search_id))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            presenter.getSearchUser(binding.searchId.getText().toString());
        }
    }

    public void onClickGiftSeedBtn(View view) {
        if (ValidationUtil.isEmptyOfEditText(binding.seed)) {
            super.showBasicOneBtnPopup(null, getString(R.string.required_order))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            presenter.sendGiftSeed(binding.findUser.getText().toString(), binding.seed.getText().toString());
        }
    }

    @Override
    public void setSendResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        if (response.getResult().matches("3")) {
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }
}
