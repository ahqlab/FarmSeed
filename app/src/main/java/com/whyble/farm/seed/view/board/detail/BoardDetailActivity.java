package com.whyble.farm.seed.view.board.detail;

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
import com.whyble.farm.seed.MainActivity;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityBoardDetailBinding;
import com.whyble.farm.seed.domain.notice.Notice;
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.view.board.BoardActivity;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;

public class BoardDetailActivity extends BaseActivity<BoardDetailActivity> implements BoardDetailIn.View,  NavigationView.OnNavigationItemSelectedListener{

    ActivityBoardDetailBinding binding;

    BoardDetailIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_detail);
        binding.setActivity(BoardDetailActivity.this);
        presenter = new BoardDetailPresenter(this);
        presenter.loadData(BoardDetailActivity.this);
        Intent intent = getIntent();
        String no = intent.getStringExtra("No");
        presenter.getDetail(no);


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
    protected BaseActivity<BoardDetailActivity> getActivityClass() {
        return BoardDetailActivity.this;
    }

    public void onClickBackBtn(View view){
        finish();
    }

    @Override
    public void setBoardResult(String s) {
        Gson gson = new Gson();
        Notice notice = gson.fromJson(s, Notice.class);
        binding.setDomain(notice);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.draw_save_seed){
            ((BoardActivity)BoardActivity.mContext).finish();
            Intent intent = new Intent(getApplicationContext(), SaveSeedActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.draw_farm_seed){
            ((BoardActivity)BoardActivity.mContext).finish();
            Intent intent = new Intent(getApplicationContext(), FarmSeedActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.draw_harvest_history){
            ((BoardActivity)BoardActivity.mContext).finish();
            Intent intent = new Intent(getApplicationContext(), MySeedActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.draw_bonus_seed){
            ((BoardActivity)BoardActivity.mContext).finish();
            Intent intent = new Intent(getApplicationContext(), BonusSeedActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.logout){
            ((BoardActivity)BoardActivity.mContext).finish();
            ((MainActivity)MainActivity.mContext).finish();
            mSharedPrefManager.removeAllPreferences();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
