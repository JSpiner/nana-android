package com.planet.nana.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.planet.nana.R;
import com.planet.nana.databinding.ActivitySplashBinding;
import com.planet.nana.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentBinding(R.layout.activity_splash);
    }
}
