package com.whyble.farm.seed.view.friend.invite;

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

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.whyble.farm.seed.MainActivity;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.SharedPrefManager;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityInviteFriendBinding;
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.util.TextManager.TextManager;
import com.whyble.farm.seed.util.device.DeviceUtils;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;

import java.io.Serializable;

import lombok.Data;

public class InviteFriendActivity extends BaseActivity<InviteFriendActivity> implements InviteFriendIn.View,  NavigationView.OnNavigationItemSelectedListener{

    ActivityInviteFriendBinding binding;

    InviteFriendIn.Presenter presenter;

    SharedPrefManager sharedPrefManager;

    FriendResultResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invite_friend);
        binding.setActivity(InviteFriendActivity.this);
        sharedPrefManager = SharedPrefManager.getInstance(InviteFriendActivity.this);
        presenter = new InviteFriendPresenter(InviteFriendActivity.this);
        presenter.loadData(InviteFriendActivity.this);
        presenter.getFriendInfo();
        binding.blockId.setText("개인 블럭 ID : " + sharedPrefManager.getStringExtra(TextManager.VALID_USER));
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
    protected BaseActivity<InviteFriendActivity> getActivityClass() {
        return InviteFriendActivity.this;
    }


    public void onClickCopyAddress(View view){
        if(result != null){
            DeviceUtils.setClipBoardLink(InviteFriendActivity.this, result.getCode());
        }
    }

    public void onClickQRDownload(View view){

    }

    @Override
    public void setFriendInfo(String s) {
        if(s != null){
            Gson gson = new Gson();
            result = gson.fromJson(s, FriendResultResult.class);
            Picasso.with(InviteFriendActivity.this)
                    .load(result.getImg())
                    .into(binding.qecode);

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
    public class FriendResultResult implements Serializable {

        private String code;

        private String img;
    }
}
