package com.planet.nana.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.planet.nana.R;
import com.planet.nana.api.Api;
import com.planet.nana.databinding.ActivityLoginBinding;
import com.planet.nana.ui.base.BaseActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
                showToast("id/pw를 입력해주세요.");
            }
        });
    }

    private boolean isValidInput() {
        return !TextUtils.isEmpty(binding.userId.getText()) &&
                !TextUtils.isEmpty(binding.userPw.getText());
    }

    private void requestLogin() {
        Api.getInstance().login(
                binding.userId.getText().toString(),
                binding.userPw.getText().toString()
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> showToast("로그인 되셨습니다."),
                        throwable -> showToast("id/pw를 확인해주세요."));
    }

}
