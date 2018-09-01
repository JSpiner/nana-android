package com.planet.nana.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.planet.nana.R;
import com.planet.nana.databinding.ActivityLoginBinding;
import com.planet.nana.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentBinding(R.layout.activity_login);
    }
}
