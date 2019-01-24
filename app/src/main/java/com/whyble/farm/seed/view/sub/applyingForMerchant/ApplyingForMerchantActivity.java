package com.whyble.farm.seed.view.sub.applyingForMerchant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.whyble.farm.seed.MainActivity;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.SharedPrefManager;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityApplyingForMerchantBinding;
import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.util.TextManager.TextManager;
import com.whyble.farm.seed.util.ValidationUtil;
import com.whyble.farm.seed.view.daum.DaumActivity;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;

import java.io.Serializable;

import lombok.Data;

public class ApplyingForMerchantActivity extends BaseActivity<ApplyingForMerchantActivity> implements ApplyingForMerchantIn.View ,  NavigationView.OnNavigationItemSelectedListener{

    ActivityApplyingForMerchantBinding binding;

    ApplyingForMerchantIn.Presenter presenter;

    SharedPrefManager sharedPrefManager;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_applying_for_merchant);
        binding.setActivity(this);
        binding.setDomain(new ApplyingForMerchant());
        presenter = new ApplyingForMerchantPresenter(ApplyingForMerchantActivity.this);
        presenter.loadData(ApplyingForMerchantActivity.this);
        sharedPrefManager = SharedPrefManager.getInstance(ApplyingForMerchantActivity.this);
        binding.validUser.setText(sharedPrefManager.getStringExtra(TextManager.VALID_USER));
        binding.getDomain().setValid_user(sharedPrefManager.getStringExtra(TextManager.VALID_USER));
        binding.toolbar.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivityClass(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected BaseActivity<ApplyingForMerchantActivity> getActivityClass() {
        return ApplyingForMerchantActivity.this;
    }

    public void onClickBankname(View view) {
        final String[] values = new String[]{
                "농협",
                "하나은행",
                "카카오뱅크",
                "신한은행"
        };
        AlertDialog.Builder builder = super.showBasicOneBtnPopup("은행선택", null);
        // add a radio button list
        builder.setItems(values, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                binding.mBankname.setText(values[which]);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClickZipcodeBtnClick(View view) {
        Intent i = new Intent(getActivityClass(), DaumActivity.class);
        startActivityForResult(i, TextManager.ZIPCODE_REQUST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TextManager.ZIPCODE_REQUST_CODE) {
            if (resultCode == RESULT_OK) {
                String zipcorde = data.getStringExtra("zipcorde");
                String address = data.getStringExtra("address");
                binding.mAddr1.setText(zipcorde);
                binding.mAddr2.setText(address);
            }
        }
    }

    public void onClickSubmit(View view) {
        if (ValidationUtil.isEmptyOfEditText(binding.validUser)) {
            super.showBasicOneBtnPopup(null, "아이디를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.mComname)) {
            super.showBasicOneBtnPopup(null, "상호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.mCategory)) {
            super.showBasicOneBtnPopup(null, "업종을 입력하세요.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.mNumber)) {
            super.showBasicOneBtnPopup(null, "사업자 번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.mAddr1)) {
            super.showBasicOneBtnPopup(null, "우편번호을 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.mAddr2)) {
            super.showBasicOneBtnPopup(null, "주소를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.mAddr3)) {
            super.showBasicOneBtnPopup(null, "상세주소를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.mTel)) {
            super.showBasicOneBtnPopup(null, "전화번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.mBankname)) {
            super.showBasicOneBtnPopup(null, "은행명을 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.mBanknum)) {
            super.showBasicOneBtnPopup(null, "계좌번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.mContent)) {
            super.showBasicOneBtnPopup(null, "업체 또는 서비스 설명을 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else{
            Log.e("HJLEE", "<> RESULT : " + binding.getDomain());
            presenter.regist(binding.getDomain());
        }

    }

    @Override
    public void setRegistResult(String s) {
        Log.e("HJLEE", "s : " + s);
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        Log.e("HJLEE", "response : " + response.toString());
        if (response.getResult().matches("0")) {
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.draw_save_seed){
            Intent intent = new Intent(getApplicationContext(), SaveSeedActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.draw_farm_seed){
            Intent intent = new Intent(getApplicationContext(), FarmSeedActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.draw_harvest_history){
            Intent intent = new Intent(getApplicationContext(), MySeedActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.draw_bonus_seed){
            Intent intent = new Intent(getApplicationContext(), BonusSeedActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.logout){
            mSharedPrefManager.removeAllPreferences();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            ((MainActivity)MainActivity.mContext).finish();
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Data
    public class ApplyingForMerchant implements Serializable {


        private String valid_user;

        private String m_comname;

        private String m_category;

        private String m_name;

        private String m_number;

        private String m_addr1;

        private String m_addr2;

        private String m_addr3;

        private String m_tel;

        private String m_email;

        private String m_content;

        private String m_bankname;

        private String m_banknum;
    }

}
