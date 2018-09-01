package com.planet.nana.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(FirebaseInstanceIdService.class.getSimpleName(), "fcm token : " + refreshedToken);
    }
}
