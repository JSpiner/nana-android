package com.planet.nana.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.planet.nana.R;
import com.planet.nana.databinding.ActivitySplashBinding;
import com.planet.nana.ui.base.BaseActivity;
import com.planet.nana.ui.login.LoginActivity;
import com.planet.nana.ui.main.MainActivity;
import com.planet.nana.util.Prefer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    private static final String[] REQUEST_PERMISSIONS = new String[] {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentBinding(R.layout.activity_splash);

        init();
    }

    private void init() {
        checkPermission();
    }

    private void checkPermission() {
        TedPermission.with(this)
                .setPermissions(REQUEST_PERMISSIONS)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Completable.timer(2000, TimeUnit.MILLISECONDS)
                                .subscribe(SplashActivity.this::checkLogin);
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        showToast( "권한 허용이 필요합니다!");
                        checkPermission();
                    }
                })
                .check();
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
