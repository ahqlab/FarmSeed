package com.whyble.farm.seed.view.board;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.whyble.farm.seed.MainActivity;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.adapter.AbsractCommonAdapter;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityBoardBinding;
import com.whyble.farm.seed.databinding.BoardListviewItemBinding;
import com.whyble.farm.seed.domain.notice.Notice;
import com.whyble.farm.seed.domain.notice.NoticeList;
import com.whyble.farm.seed.domain.seeds.save.SaveList;
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.view.board.detail.BoardDetailActivity;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;

import java.util.List;

public class BoardActivity extends BaseActivity<BoardActivity> implements BoardIn.View,  NavigationView.OnNavigationItemSelectedListener{

    public static Context mContext;

    ActivityBoardBinding binding;

    BoardIn.Presenter presenter;

    AbsractCommonAdapter<Notice> boardSeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board);
        binding.setActivity(BoardActivity.this);
        presenter = new BoardPresenter(BoardActivity.this);
        presenter.loadData(BoardActivity.this);
        presenter.getBoards();
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
        presenter.getBoards();
    }

    @Override
    protected BaseActivity<BoardActivity> getActivityClass() {
        return BoardActivity.this;
    }

    @Override
    public void setBoards(String s) {
        Gson gson = new Gson();
        NoticeList response = gson.fromJson(s, NoticeList.class);
        if(response.getNotice() != null){
            setNoticeList(response.getNotice());
        }
    }

    public void onClickBackBtn(View view){
        finish();
    }

    private void setNoticeList(List<Notice> list) {
        boardSeedAdapter = new AbsractCommonAdapter<Notice>(BoardActivity.this, list) {

            BoardListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = boardSeedAdapter.inflater.inflate(R.layout.board_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(boardSeedAdapter.data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (BoardListviewItemBinding) convertView.getTag();
                    adapterBinding.setDomain(boardSeedAdapter.data.get(position));
                }

                Log.e("HJLEE", ">>>" + adapterBinding.getDomain().getTitle().length());
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BoardActivity.super.showToast(adapterBinding.getDomain().getNo());
                        Intent intent = new Intent(getApplicationContext(), BoardDetailActivity.class);
                        intent.putExtra("No", adapterBinding.getDomain().getNo());
                        startActivity(intent);
                        //finish();
                    }
                });
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return false;
                    }
                });
                return adapterBinding.getRoot();
            }
        };
        binding.seedListview.setAdapter(boardSeedAdapter);
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
