package com.whyble.farm.seed.view.sub.findMerchants;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.whyble.farm.seed.MainActivity;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.adapter.AbsractCommonAdapter;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityFindMerchantsBinding;
import com.whyble.farm.seed.databinding.AllBonusSeedListviewItemBinding;
import com.whyble.farm.seed.databinding.MemberShipListviewItemBinding;
import com.whyble.farm.seed.domain.Domain;
import com.whyble.farm.seed.domain.seeds.bonus.all.Bonus;
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.util.ValidationUtil;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

public class FindMerchantsActivity extends BaseActivity<FindMerchantsActivity> implements MapView.MapViewEventListener, MapView.POIItemEventListener, FindMerchantsIn.View, NavigationView.OnNavigationItemSelectedListener {

    ActivityFindMerchantsBinding binding;

    FindMerchantsIn.Presenter presenter;

    private MapView mMapView;

    private MapPOIItem mDefaultMarker;

    private MapPoint point;

    private InputMethodManager imm;

    AbsractCommonAdapter<MemberShip> memnberShipAdapter;

    @Override
    public void searchResult(String s) {
        Gson gson = new Gson();
        SearchResult result = gson.fromJson(s, SearchResult.class);
        if (result.getX() != null) {
            point = MapPoint.mapPointWithGeoCoord(Float.parseFloat(result.y), Float.parseFloat(result.x));
            mMapView.removeAllPOIItems();
            mMapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter(result));
            createDefaultMarker(mMapView, point);
            showAll();
        } else {
            super.showBasicOneBtnPopup(null, "검색결과가 없습니다.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mMapView.removeAllPOIItems();
                            dialog.dismiss();
                        }
                    }).show();
        }

    }

    @Override
    public void setChainList(String s) {
        if (s != null) {
            Gson gson = new Gson();
            MemberShipList list = gson.fromJson(s, MemberShipList.class);
            setListview(list.getMembership_list());
        }
    }

    private void setListview(List<MemberShip> list) {

        binding.listCount.setText(String.valueOf(list.size()));
        memnberShipAdapter = new AbsractCommonAdapter<MemberShip>(FindMerchantsActivity.this, list) {

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
        binding.listview.setAdapter(memnberShipAdapter);
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


    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        private SearchResult searchResult;

        public CustomCalloutBalloonAdapter(SearchResult searchResult) {
            this.searchResult = searchResult;
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            ((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.ic_launcher);
            ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(searchResult.getM_comname());
            ((TextView) mCalloutBalloon.findViewById(R.id.desc)).setText(searchResult.getAddr());
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }

    @Data
    public class SearchResult implements Serializable {
        private String x;
        private String y;
        private String m_comname;
        private String addr;
    }

    @Data
    public class MemberShipList implements Serializable {
        List<MemberShip> membership_list;
    }

    @Data
    public class MemberShip implements Serializable {
        private String m_comname;
        private String m_category;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_find_merchants);
        binding = DataBindingUtil.setContentView(FindMerchantsActivity.this, R.layout.activity_find_merchants);
        binding.setActivity(this);
        presenter = new FindMerchantsPresenter(FindMerchantsActivity.this);
        presenter.loadData(FindMerchantsActivity.this);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.setDaumMapApiKey("86a3e5cb2b0ccd3db9c4e573a36b62ef");
        mMapView.setMapViewEventListener(this);
        mMapView.setPOIItemEventListener(this);
        mMapView.setZoomLevel(2, true);

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
    protected BaseActivity<FindMerchantsActivity> getActivityClass() {
        return FindMerchantsActivity.this;
    }

    public void findMerchants(View view) {
        if (ValidationUtil.isEmptyOfEditText(binding.editText)) {
            super.showBasicOneBtnPopup(null, "검색어를 입력하세요.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            presenter.searchMerchants(binding.editText.getText().toString());
            imm.hideSoftInputFromWindow(binding.editText.getWindowToken(), 0);
        }
    }

    private void showAll() {
    }

    private void createDefaultMarker(MapView mapView, MapPoint point) {
        mDefaultMarker = new MapPOIItem();
        String name = "Default Marker";
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setTag(0);
        mDefaultMarker.setMapPoint(point);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(point, false);
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    protected void onResume() {
        presenter.getChainList();
        super.onResume();
    }
}