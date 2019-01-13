package com.whyble.farm.seed.view.sub.giftSeed;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whyble.farm.seed.MainActivity;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityGifiSeedBinding;
import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.domain.gift.giftSeed.Cash;
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.util.MathUtil;
import com.whyble.farm.seed.util.ValidationUtil;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;

public class GifiSeedActivity extends BaseActivity<GifiSeedActivity> implements GifiSeedIn.View ,  NavigationView.OnNavigationItemSelectedListener{

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
    protected void onResume() {
        presenter.getMyTotlaSeeds();
        presenter.currentMySeed();
        super.onResume();
    }

    @Override
    protected BaseActivity<GifiSeedActivity> getActivityClass() {
        return GifiSeedActivity.this;
    }


    public void onClickBackBtn(View view){
        finish();
    }

    @Override
    public void setMyTotalSeed(String s) {

    }

    @Override
    public void setSavePoint(String save_point) {
        if(save_point != null){
            binding.saveSeedPoint.setText(MathUtil.stringToMoneyType(save_point));
        }
    }

    @Override
    public void setFarmPoint(String farm_point) {
        if(farm_point != null){
            binding.farmSeedPoint.setText(MathUtil.stringToMoneyType(farm_point));
        }
    }

    @Override
    public void setCashPoint(String cash_point) {
        if(cash_point != null){
            binding.cashSeedPoint.setText(MathUtil.stringToMoneyType(cash_point));
        }
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
        presenter.getMyTotlaSeeds();
        presenter.currentMySeed();
    }

    @Override
    public void setCurrentMySeed(String s) {
        Log.e("HJLEE", "s : " + s);
        if(s != null){
            Gson gson = new Gson();
            Cash cash = gson.fromJson(s, Cash.class);
            Log.e("HJLEE", "Cash : " + cash);
            if(cash.getCash().getCash() != null){
                binding.currentMySeed.setText(MathUtil.stringToMoneyType(cash.getCash().getCash()) + "Seed");
            }
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
}
