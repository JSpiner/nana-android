package com.planet.nana.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.planet.nana.R;
import com.planet.nana.databinding.ActivitySplashBinding;
import com.planet.nana.ui.base.BaseActivity;
import com.planet.nana.ui.login.LoginActivity;
import com.planet.nana.ui.main.MainActivity;
import com.planet.nana.util.Prefer;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentBinding(R.layout.activity_splash);

        init();
    }

    private void init() {
        Completable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribe(this::checkLogin);
    }

    private void checkLogin() {
        if (isLogined()) {
            startMainActivity();
        }
        else {
            startLoginActivity();
        }
    }

    private boolean isLogined() {
        return !TextUtils.isEmpty(Prefer.getString(Prefer.KEY_LOGINED_ID));
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
