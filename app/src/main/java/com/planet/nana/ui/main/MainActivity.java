package com.planet.nana.ui.main;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.planet.nana.R;
import com.planet.nana.databinding.ActivityMainBinding;
import com.planet.nana.service.LocationListenService;
import com.planet.nana.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentBinding(R.layout.activity_main);

        initMap();
        startLocationService();
    }

    private void initMap() {
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {
            
        });
    }

    private void startLocationService() {
        Intent intent = new Intent(this, LocationListenService.class);
        startService(intent);
    }
}
