package com.planet.nana.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.planet.nana.R;
import com.planet.nana.databinding.ActivityMainBinding;
import com.planet.nana.service.LocationListenService;
import com.planet.nana.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentBinding(R.layout.activity_main);

        startLocationService();
    }

    private void startLocationService() {
        Intent intent = new Intent(this, LocationListenService.class);
        startService(intent);
    }
}
