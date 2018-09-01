package com.planet.nana.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.planet.nana.util.Prefer;

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Prefer.putString(Prefer.KEY_PUSH_TOKEN, refreshedToken);
        Log.i(FirebaseInstanceIdService.class.getSimpleName(), "fcm token : " + refreshedToken);
    }
}
