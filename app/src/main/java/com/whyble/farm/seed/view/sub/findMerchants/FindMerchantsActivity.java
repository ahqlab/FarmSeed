package com.whyble.farm.seed.view.sub.findMerchants;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityFindMerchantsBinding;
import com.whyble.farm.seed.util.ValidationUtil;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.List;

import lombok.Data;

public class FindMerchantsActivity extends BaseActivity<FindMerchantsActivity> implements MapView.MapViewEventListener, MapView.POIItemEventListener, FindMerchantsIn.View {

    ActivityFindMerchantsBinding binding;

    FindMerchantsIn.Presenter presenter;

    private MapView mMapView;

    private MapPOIItem mDefaultMarker;

    private MapPoint point;

    private InputMethodManager imm;

    @Override
    public void searchResult(String s) {
        Log.e("HJLEE", s);
        if (s != null) {
            Gson gson = new Gson();
            SearchResult result = gson.fromJson(s, SearchResult.class);
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
                            dialog.dismiss();
                        }
                    }).show();
        }

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
    class SearchResult {
        private String x;
        private String y;
        private String m_comname;
        private String addr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_find_merchants);
        binding = DataBindingUtil.setContentView(FindMerchantsActivity.this, R.layout.activity_find_merchants);
        binding.setActivity(FindMerchantsActivity.this);
        presenter = new FindMerchantsPresenter(FindMerchantsActivity.this);
        presenter.loadData(FindMerchantsActivity.this);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.setDaumMapApiKey("56cd248623a6215cef9ee05a30357d21");
        mMapView.setMapViewEventListener(this);
        mMapView.setPOIItemEventListener(this);
        mMapView.setZoomLevel(2, true);

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

}