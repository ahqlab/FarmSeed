package com.whyble.farm.seed.view.sub.giftHistory;

import android.Manifest;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whyble.farm.seed.MainActivity;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.adapter.AbsractCommonAdapter;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityGiftSeedHistoryBinding;
import com.whyble.farm.seed.databinding.GiftSeedListviewItemBinding;
import com.whyble.farm.seed.domain.gift.list.Gift;
import com.whyble.farm.seed.domain.gift.list.GiftList;
import com.whyble.farm.seed.domain.gift.list.TotalGift;
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;

import java.util.List;

public class GiftSeedHistoryActivity extends BaseActivity<GiftSeedHistoryActivity> implements GiftSeedHistoryIn.View,  NavigationView.OnNavigationItemSelectedListener{

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
        super.onResume();
        presenter.getSeeds();
    }

    @Override
    protected BaseActivity<GiftSeedHistoryActivity> getActivityClass() {
        return GiftSeedHistoryActivity.this;
    }

    public void onClickBackBtn(View view){
        finish();
    }

    @Override
    public void getSeedResult(String s) {
        Gson gson = new Gson();
        GiftList response = gson.fromJson(s, GiftList.class);
        if(response.getGift_list() != null){
            setSaveSeedList(response.getGift_list());
            setTotalSeed(response.getPl_mi());
        }
    }

    private void setSaveSeedList(List<Gift> list) {
        if(list != null){
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
    }

    private void setTotalSeed(TotalGift totalGift) {
        binding.totalSeed.setText("전체 : " + totalGift.getPlus() + " / " + totalGift.getMinus());
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
