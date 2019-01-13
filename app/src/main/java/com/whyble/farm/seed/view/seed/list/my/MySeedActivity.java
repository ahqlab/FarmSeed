package com.whyble.farm.seed.view.seed.list.my;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whyble.farm.seed.MainActivity;
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
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.util.ViewUtil;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;

import java.util.List;

public class MySeedActivity extends BaseActivity<MySeedActivity> implements MySeedIn.View ,  NavigationView.OnNavigationItemSelectedListener{

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
