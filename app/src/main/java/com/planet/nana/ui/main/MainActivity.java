package com.planet.nana.ui.main;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.planet.nana.R;
import com.planet.nana.api.Api;
import com.planet.nana.databinding.ActivityMainBinding;
import com.planet.nana.model.Zone;
import com.planet.nana.service.LocationListenService;
import com.planet.nana.ui.base.BaseActivity;
import com.planet.nana.util.Prefer;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentBinding(R.layout.activity_main);

        initToolbar();
        initMap();
        startLocationService();
    }

    private void initToolbar() {
        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void initMap() {
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {
            MainActivity.this.googleMap = googleMap;
            LatLng initPosition = new LatLng(37.5042213,127.0445732);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(initPosition));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
            requestZoneList();
        });
    }

    private void startLocationService() {
        Intent intent = new Intent(this, LocationListenService.class);
        startService(intent);
    }

    private void requestZoneList() {
        Api.getInstance().getZoneList(
                Prefer.getString(Prefer.KEY_LOGINED_ID)
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapObservable(Observable::fromIterable)
                .subscribe(this::markZone);
    }

    private void markZone(Zone zone) {
        double lat = zone.getLatitude();
        double lng = zone.getLongitude();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(lat, lng))
                .title("마커")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker));
        googleMap.addMarker(markerOptions);

        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(new LatLng(lat, lng));
        circleOptions.strokeWidth(0);
        circleOptions.radius(zone.getRadius() * 1000);
        circleOptions.fillColor(0x30b0d0ff);
        circleOptions.strokeWidth(2);

        googleMap.addCircle(circleOptions);
    }
}
