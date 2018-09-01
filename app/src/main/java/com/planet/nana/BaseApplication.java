package com.planet.nana;

import android.app.Application;

public class BaseApplication extends Application {

    public static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        BaseApplication.application = this;
    }
}
