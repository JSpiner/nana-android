package com.planet.nana;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BaseApplication extends Application {

    public static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        BaseApplication.application = this;

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/helvetica.ttc")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
