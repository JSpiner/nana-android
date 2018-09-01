package com.planet.nana.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.planet.nana.R;
import com.planet.nana.databinding.ActivityLoginBinding;
import com.planet.nana.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentBinding(R.layout.activity_login);

        init();
    }

    private void init() {
        binding.login.setOnClickListener(__ -> {
            if (isValidInput()) {
                requestLogin();
            }
            else {
                Toast.makeText(this, "id/pw를 입력해주세요.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isValidInput() {
        return !TextUtils.isEmpty(binding.userId.getText()) &&
                !TextUtils.isEmpty(binding.userPw.getText());
    }

    private void requestLogin() {
        
    }

}
