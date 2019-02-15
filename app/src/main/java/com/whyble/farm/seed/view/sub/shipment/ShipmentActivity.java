package com.whyble.farm.seed.view.sub.shipment;

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
import com.whyble.farm.seed.databinding.ActivityShipmentBinding;
import com.whyble.farm.seed.databinding.AllFarmSeedListviewItemBinding;
import com.whyble.farm.seed.databinding.AllShipmentSeedListviewItemBinding;
import com.whyble.farm.seed.domain.seeds.farm.all.Farm;
import com.whyble.farm.seed.domain.seeds.farm.all.FarmList;
import com.whyble.farm.seed.domain.seeds.shipment.Shipment;
import com.whyble.farm.seed.domain.seeds.shipment.ShipmentList;
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedPresenter;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;

import java.util.List;

public class ShipmentActivity extends BaseActivity<ShipmentActivity> implements ShipmentIn.View, NavigationView.OnNavigationItemSelectedListener {

    ActivityShipmentBinding binding;

    ShipmentIn.Presenter presenter;

    AbsractCommonAdapter<Shipment> farmSeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment);
        binding = DataBindingUtil.setContentView(ShipmentActivity.this, R.layout.activity_shipment);
        binding.setActivity(ShipmentActivity.this);
        presenter = new ShipmentPresenter(ShipmentActivity.this);
        presenter.loadData(ShipmentActivity.this);
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
    protected BaseActivity<ShipmentActivity> getActivityClass() {
        return ShipmentActivity.this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getSeeds();
    }


    public void onClickBackBtn(View view) {
        finish();
    }

    @Override
    public void getSeedResult(String s) {
        Gson gson = new Gson();
        ShipmentList response = gson.fromJson(s, ShipmentList.class);
        if (response.getCash_list() != null) {
            setSaveSeedList(response.getCash_list());
            setTotalSeed(response.getCash());
        }
    }

    private void setTotalSeed(String seed) {
        binding.totalSeed.setText("전체 Seed : " + seed);
    }

    public void setSaveSeedList(List<Shipment> list) {
        if (list != null) {


            farmSeedAdapter = new AbsractCommonAdapter<Shipment>(ShipmentActivity.this, list) {

                AllShipmentSeedListviewItemBinding adapterBinding;

                @Override
                protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = farmSeedAdapter.inflater.inflate(R.layout.all_shipment_seed_listview_item, null);
                        adapterBinding = DataBindingUtil.bind(convertView);
                        adapterBinding.setDomain(farmSeedAdapter.data.get(position));
                        convertView.setTag(adapterBinding);
                    } else {
                        adapterBinding = (AllShipmentSeedListviewItemBinding) convertView.getTag();
                        adapterBinding.setDomain(farmSeedAdapter.data.get(position));
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
            binding.seedListview.setAdapter(farmSeedAdapter);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.draw_save_seed) {
            Intent intent = new Intent(getApplicationContext(), SaveSeedActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.draw_farm_seed) {
            Intent intent = new Intent(getApplicationContext(), FarmSeedActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.draw_harvest_history) {
            Intent intent = new Intent(getApplicationContext(), MySeedActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.draw_bonus_seed) {
            Intent intent = new Intent(getApplicationContext(), BonusSeedActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.logout) {
            mSharedPrefManager.removeAllPreferences();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            ((MainActivity) MainActivity.mContext).finish();
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
