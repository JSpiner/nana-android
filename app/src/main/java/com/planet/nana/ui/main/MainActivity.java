package com.planet.nana.ui.main;

import android.os.Bundle;

import com.planet.nana.R;
import com.planet.nana.databinding.ActivityMainBinding;
import com.planet.nana.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentBinding(R.layout.activity_main);
    }
}
