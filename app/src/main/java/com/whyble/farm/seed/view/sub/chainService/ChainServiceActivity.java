package com.whyble.farm.seed.view.sub.chainService;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.whyble.farm.seed.MainActivity;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.adapter.AbsractCommonAdapter;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityChainServiceBinding;
import com.whyble.farm.seed.databinding.MemberShipListviewItemBinding;
import com.whyble.farm.seed.domain.seeds.bonus.all.Bonus;
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.util.ViewUtil;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedPresenter;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;
import com.whyble.farm.seed.view.sub.findMerchants.FindMerchantsActivity;

import java.util.List;

public class ChainServiceActivity extends BaseActivity<ChainServiceActivity> implements ChainServiceIn.View , NavigationView.OnNavigationItemSelectedListener{

    ActivityChainServiceBinding binding;

    ChainServiceIn.Presenter presenter;

    AbsractCommonAdapter<FindMerchantsActivity.MemberShip> memnberShipAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain_service);
        binding = DataBindingUtil.setContentView(ChainServiceActivity.this, R.layout.activity_chain_service);
        binding.setActivity(ChainServiceActivity.this);
        presenter = new ChainServicePresenter(ChainServiceActivity.this);
        presenter.loadData(ChainServiceActivity.this);
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
    protected BaseActivity<ChainServiceActivity> getActivityClass() {
        return ChainServiceActivity.this;
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

    @Override
    public void getSeedResult(String s) {
        if (s != null) {
            Gson gson = new Gson();
            FindMerchantsActivity.MemberShipList list = gson.fromJson(s, FindMerchantsActivity.MemberShipList.class);
            setListview(list.getMembership_list());
        }
    }

    public void onClickBackBtn(View view){
        finish();
    }

    private void setListview(List<FindMerchantsActivity.MemberShip> list) {

        if(list != null){
            memnberShipAdapter = new AbsractCommonAdapter<FindMerchantsActivity.MemberShip>(ChainServiceActivity.this, list) {

                MemberShipListviewItemBinding adapterBinding;

                @Override
                protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = memnberShipAdapter.inflater.inflate(R.layout.member_ship_listview_item, null);
                        adapterBinding = DataBindingUtil.bind(convertView);
                        adapterBinding.setDomain(memnberShipAdapter.data.get(position));
                        convertView.setTag(adapterBinding);
                    } else {
                        adapterBinding = (MemberShipListviewItemBinding) convertView.getTag();
                        adapterBinding.setDomain(memnberShipAdapter.data.get(position));
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
            binding.seedListview.setAdapter(memnberShipAdapter);
        }
    }
}
