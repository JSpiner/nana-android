package com.planet.nana.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.planet.nana.BaseApplication;

public final class Prefer {

    public static final String KEY_LOGINED_ID = "keyLoginedId";
    public static final String KEY_PUSH_TOKEN = "keyPushToken";

    private static final String PREFER_NAME = "prefer";

    private static SharedPreferences getSharedPreference() {
        Context context = BaseApplication.application;
        return context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
    }

    public static void putString(String key, String value) {
        getSharedPreference().edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return getSharedPreference().getString(key, null);
    }

}
