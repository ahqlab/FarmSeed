package com.whyble.farm.seed.view.seed.list.save;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.adapter.AbsractCommonAdapter;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivitySaveSeedBinding;
import com.whyble.farm.seed.databinding.AllSaveSeedListviewItemBinding;
import com.whyble.farm.seed.databinding.SaveSeedListviewItemBinding;
import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.domain.seeds.save.SaveList;
import com.whyble.farm.seed.domain.seeds.save.AllSaveList;
import com.whyble.farm.seed.domain.seeds.save.Save;

import java.util.List;

public class SaveSeedActivity extends BaseActivity<SaveSeedActivity> implements SaveSeedIn.View {

    ActivitySaveSeedBinding binding;

    SaveSeedIn.Presenter presenter;

    AbsractCommonAdapter<SaveList> saveSeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_save_seed);
        binding.setActivity(this);
        presenter = new SaveSeedPresenter(this);
        presenter.loadData(SaveSeedActivity.this);
        presenter.getSeeds();
        binding.toolbar.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }

   /* public void openCamera() {
        IntentIntegrator integrator = new IntentIntegrator(getActivityClass());
        integrator.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setScanningRectangle(800, 800);
        integrator.setPrompt(getString(R.string.qr_message));
        integrator.initiateScan();
    }*/

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.CAMERA)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            openCamera(); // start WIFIScan
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.required_permission_message), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("HJLEE", ">>" +resultCode);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivityClass(), R.string.cancelled, Toast.LENGTH_LONG).show();
            } else {
                coinWalletAddress.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/


    public void onClickSendRe(View view) {
        presenter.sendRe(binding.order.getText().toString());
    }

    @Override
    protected BaseActivity<SaveSeedActivity> getActivityClass() {
        return SaveSeedActivity.this;
    }

    @Override
    public void getSeedResult(String s) {
        Gson gson = new Gson();
        AllSaveList response = gson.fromJson(s, AllSaveList.class);
        setSaveSeedList(response.getSave_list());
        setTotalSeed(response.getSave());
    }

    @Override
    public void setSendReResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        if (response.getResult().matches("2")) {
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

    private void setTotalSeed(String seed) {
        binding.totalSeed.setText("전체 Seed : " + seed);
        binding.myTotalSeed.setText("전체 Seed : " + seed);
    }

    public void setSaveSeedList(List<SaveList> list) {
        saveSeedAdapter = new AbsractCommonAdapter<SaveList>(SaveSeedActivity.this, list) {

            AllSaveSeedListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = saveSeedAdapter.inflater.inflate(R.layout.all_save_seed_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(saveSeedAdapter.data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (AllSaveSeedListviewItemBinding) convertView.getTag();
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
        binding.seedListview.setAdapter(saveSeedAdapter);
    }
}
