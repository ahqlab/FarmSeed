package com.whyble.farm.seed.view.sub.neighborBlock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.whyble.farm.seed.MainActivity;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityNeighborBlockBinding;
import com.whyble.farm.seed.domain.neighborBlock.Block1;
import com.whyble.farm.seed.domain.neighborBlock.Block2;
import com.whyble.farm.seed.domain.neighborBlock.NeighborBlockList;
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;
import com.whyble.farm.seed.view.sub.neighborBlock.adapter.BaseExpandableAdapter;

import java.util.ArrayList;
import java.util.List;


//NeighborBlock
public class NeighborBlockActivity extends BaseActivity<NeighborBlockActivity> implements NeighborBlockIn.View, NavigationView.OnNavigationItemSelectedListener {

    ActivityNeighborBlockBinding binding;

    NeighborBlockIn.Presenter presenter;

    private ArrayList<Block1> mGroupList = null;

    private ArrayList<ArrayList<Block2>> mChildList = null;

    private ArrayList<Block2> mChildListContent = null;

    private BaseExpandableAdapter baseExpandableAdapter;

    android.app.AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_neighbor_block);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_neighbor_block);
        binding.setActivity(NeighborBlockActivity.this);
        presenter = new NeighborBlockPresenter(NeighborBlockActivity.this);
        presenter.loadData(NeighborBlockActivity.this);
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
    protected BaseActivity<NeighborBlockActivity> getActivityClass() {
        return NeighborBlockActivity.this;
    }

    public void onClickBackBtn(View view) {
        finish();
    }

    @Override
    public void getSeedResult(String s) {
        Gson gson = new Gson();
        NeighborBlockList response = gson.fromJson(s, NeighborBlockList.class);
        if (Integer.parseInt(response.getTotal()) > 0) {
            setSaveSeedList(response.getBlock1(), response.getBlock2());
            setTotalSeed(response.getTotal());
        } else {

        }
    }

    @Override
    public void setDialogView(String s) {
        Gson gson = new Gson();
        NeighborBlockList response = gson.fromJson(s, NeighborBlockList.class);
        if (Integer.parseInt(response.getTotal()) > 0) {
            setDialogSetting(response.getBlock1(), response.getBlock2());
        }

    }

    private void setDialogSetting(List<Block1> list, List<Block2> list2) {
        ArrayList<Block1>  mGroupList = new ArrayList<Block1>();
        ArrayList<ArrayList<Block2>> mChildList = new ArrayList<ArrayList<Block2>>();
        ArrayList<Block2> mChildListContent = new ArrayList<Block2>();

        for (int i = 0; i < list.size(); i++) {
            mGroupList.add(list.get(i));
            mChildListContent = new ArrayList<Block2>();
            for (int j = 0; j < list2.size(); j++) {
                if (list.get(i).getUser_id().matches(list2.get(j).getUser_id())) {
                    mChildListContent.add(list2.get(j));
                }
            }
            mChildList.add(mChildListContent);
        }

        BaseExpandableAdapter adapter = new BaseExpandableAdapter(this, mGroupList, mChildList);

        LayoutInflater inflater = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        View customView = inflater.inflate(R.layout.custom_neigbor_block_dialog, null, false);
        ExpandableListView listView = (ExpandableListView) customView.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        builder =  showBasicOneBtnPopup(null, null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setView(customView);
        builder.show();

    }

    private void setTotalSeed(String seed) {
        binding.totalSeed.setText("전체블록 : " + seed);
    }

    //abhiandroid

    public void setSaveSeedList(List<Block1> list, List<Block2> list2) {

        mGroupList = new ArrayList<Block1>();
        mChildList = new ArrayList<ArrayList<Block2>>();

        for (int i = 0; i < list.size(); i++) {
            mGroupList.add(list.get(i));
            mChildListContent = new ArrayList<Block2>();
            for (int j = 0; j < list2.size(); j++) {
                if (list.get(i).getUser_id().matches(list2.get(j).getUser_id())) {
                    mChildListContent.add(list2.get(j));
                }
            }
            mChildList.add(mChildListContent);
        }

        baseExpandableAdapter = new BaseExpandableAdapter(this, mGroupList, mChildList);

        binding.seedListview.setAdapter(baseExpandableAdapter);

        // 그룹 클릭 했을 경우 이벤트
        binding.seedListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // 차일드 클릭 했을 경우 이벤트
        binding.seedListview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(baseExpandableAdapter.getChild(groupPosition, childPosition) != null){
                    showCustomDialog(baseExpandableAdapter.getChild(groupPosition, childPosition).getUser_id2());
                }
                return false;
            }
        });

        // 그룹이 닫힐 경우 이벤트
        binding.seedListview.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        // 그룹이 열릴 경우 이벤트
        binding.seedListview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });


        // 그룹이 닫힐 경우 이벤트
        binding.seedListview.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        // 그룹이 열릴 경우 이벤트
        binding.seedListview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

    }

    public void showCustomDialog(String userId) {
        presenter.getSeeds(userId);

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
