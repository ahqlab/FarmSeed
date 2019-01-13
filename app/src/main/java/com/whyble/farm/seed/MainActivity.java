package com.whyble.farm.seed;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whyble.farm.seed.common.SharedPrefManager;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.common.base.BaseNoQRActivity;
import com.whyble.farm.seed.databinding.ActivityMainBinding;
import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.domain.myFarm.TotalSeeds;
import com.whyble.farm.seed.domain.seeds.Seeds;
import com.whyble.farm.seed.helper.BottomNavigationViewHelper;
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.view.fragment.home.HomeFragment;
import com.whyble.farm.seed.view.fragment.bioblock.BioBlockFragment;
import com.whyble.farm.seed.view.fragment.home.HomeFragment;
import com.whyble.farm.seed.view.fragment.myfarm.MyFarmFragment;
import com.whyble.farm.seed.view.fragment.myinfo.MyInfoFragment;
import com.whyble.farm.seed.view.fragment.shopping.ShoppingFragment;
import com.whyble.farm.seed.view.qr.QrPaymentActivity;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;
import com.whyble.farm.seed.view.sub.giftHistory.GiftSeedHistoryActivity;

import org.w3c.dom.Text;

public class MainActivity extends BaseActivity<MainActivity> implements NavigationView.OnNavigationItemSelectedListener , MainIn.View {

    public static Context mContext;

    ActivityMainBinding binding;

    MainIn.Presenter presenter;

    public SharedPrefManager sharedPrefManager;

    private final long FINISH_INTERVAL_TIME = 2000;

    private static final int CAMERA_PERMISSION_CODE = 101;

    private long backPressedTime = 0;

    Seeds response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = this;
        binding.setActivity(this);
        sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());
        presenter = new MainPresenter(this);
        presenter.loadData(MainActivity.this);
        presenter.getSeeds();

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentTransaction.add(R.id.fragment_container, HomeFragment.newInstance()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void test(){

    }

    public void onClickQrcode(View videw){
        Intent intent = new Intent(MainActivity.this, QrPaymentActivity.class);
        startActivity(intent);
    }

    @Override
    protected BaseActivity<MainActivity> getActivityClass() {
        return MainActivity.this;
    }

    // Fragment 변환을 해주기 위한 부분, Fragment의 Instance를 받아서 변경
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    public void setHomeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(HomeFragment.newInstance(), "HomeFragment Tag").commitAllowingStateLoss();
        presenter.getSeeds();
    }

    public void setMyFarmFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(MyFarmFragment.newInstance(), "MyFarmFragment Tag").commitAllowingStateLoss();
        presenter.getMyTotlaSeeds();
    }

    public void setShopingragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(MyFarmFragment.newInstance(), "MyFarmFragment Tag").commitAllowingStateLoss();
        presenter.getMyTotlaSeeds();
    }

    public void setBioFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(BioBlockFragment.newInstance(), "BioBlockFragment Tag").commitAllowingStateLoss();
    }

    public void setMyInfoFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(MyInfoFragment.newInstance(), "MyInfoFragment Tag").commitAllowingStateLoss();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener  = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home_tab:
                    replaceFragment(HomeFragment.newInstance());
                    presenter.getSeeds();
                    return true;
                case R.id.farm_tab:
                    replaceFragment(MyFarmFragment.newInstance());
                    presenter.getMyTotlaSeeds();
                    return true;
                case R.id.shopping_tab:
                    replaceFragment(MyFarmFragment.newInstance());
                    return true;
                case R.id.bio_tab :
                    replaceFragment(BioBlockFragment.newInstance());
                    return true;
                case R.id.info_tab :
                    replaceFragment(MyInfoFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.draw_home){
            replaceFragment(HomeFragment.newInstance());
            presenter.getSeeds();
        }else if(id == R.id.draw_myfarm){
            replaceFragment(MyFarmFragment.newInstance());
            presenter.getMyTotlaSeeds();
        }else if(id == R.id.draw_shopping){
            replaceFragment(ShoppingFragment.newInstance());
        }else if(id == R.id.draw_bioblock){
            replaceFragment(BioBlockFragment.newInstance());
        }else if(id == R.id.draw_my_info){
            replaceFragment(MyInfoFragment.newInstance());
        }else if(id == R.id.draw_save_seed){
            Intent intent = new Intent(getApplicationContext(), SaveSeedActivity.class);
            startActivity(intent);
        }else if(id == R.id.draw_farm_seed){
            Intent intent = new Intent(getApplicationContext(), FarmSeedActivity.class);
            startActivity(intent);
        }else if(id == R.id.draw_harvest_history){
            Intent intent = new Intent(getApplicationContext(), MySeedActivity.class);
            startActivity(intent);
        }else if(id == R.id.draw_bonus_seed){
            Intent intent = new Intent(getApplicationContext(), BonusSeedActivity.class);
            startActivity(intent);
        }else if(id == R.id.logout){
            sharedPrefManager.removeAllPreferences();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void getSeedResult(String s) {

        android.support.v4.app.Fragment tf = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(tf instanceof HomeFragment){

            Log.e("HJLEE", ">>>" + s);

            Gson gson = new Gson();
            response = gson.fromJson(s, Seeds.class);
            HomeFragment homeFragment = (HomeFragment) tf;
            if(response.getSave() != null){
                homeFragment.setSaveSeedList(response.getSave());
                homeFragment.showSaveSeedEmptyView(false);
            }else{
                homeFragment.showSaveSeedEmptyView(true);
            }
            if(response.getFarm() != null){
                homeFragment.setFarmSeedList(response.getFarm());
                homeFragment.showFarmSeedEmptyView(false);
            }else{
                homeFragment.showFarmSeedEmptyView(true);
            }
            if(response.getCash() != null){
                homeFragment.setCashSeedList(response.getCash());
                homeFragment.showCashSeedEmptyView(false);
            }else{
                homeFragment.showCashSeedEmptyView(true);
            }
            if(response.getBonus() != null){
                homeFragment.setBonusSeedList(response.getBonus());
                homeFragment.showBonusSeedEmptyView(false);
            }else{
                homeFragment.showBonusSeedEmptyView(true);
            }
            if(response.getMessage() != null){
                homeFragment.setMainMessage(response.getMessage());
            }
            homeFragment.setBoardContent(response.getNotice());
        }else if(tf instanceof MyFarmFragment){

            MyFarmFragment myFarmFragment = (MyFarmFragment) tf;
            Gson gson = new Gson();
            TotalSeeds totalSeeds = gson.fromJson(s, TotalSeeds.class);
            myFarmFragment.setSavePoint(totalSeeds.getSave_point());
            myFarmFragment.setFarmPoint(totalSeeds.getFarm_point());
            myFarmFragment.setCashPoint(totalSeeds.getCash_point());
        }
    }


    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                super.onBackPressed();
            } else {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.press_back_message), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
