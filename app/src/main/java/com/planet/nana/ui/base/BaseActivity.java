package com.planet.nana.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    private B binding;

    public void setContentBinding(int layoutResID) {
        binding = DataBindingUtil.setContentView(this, layoutResID);
    }
}
