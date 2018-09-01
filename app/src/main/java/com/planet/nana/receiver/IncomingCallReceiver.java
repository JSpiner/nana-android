package com.planet.nana.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.planet.nana.model.Contact;
import com.planet.nana.model.Zone;
import com.planet.nana.repository.ContactRepository;
import com.planet.nana.repository.ZoneRepository;
import com.planet.nana.service.LocationListenService;

import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class IncomingCallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

        if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
            onCallRinging(context, number);
        }
    }

    private void onCallRinging(Context context, String number) {
        checkBlackList(context, number);
    }

    private void checkBlackList(Context context, String number) {
        ZoneRepository.getInstance().getFilterZoneList()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapObservable(Observable::fromIterable)
                .filter(this::isInMyLocation)
                .filter(zone -> hasMyNumber(zone, number))
                .subscribe(zone -> endCurrentCall(context, number));
    }

    private boolean isInMyLocation(Zone zone) {
        Location myLocation = LocationListenService.lastTrackedLocation;

        float[] result =new float[5];
        Location.distanceBetween(
                myLocation.getLatitude(),
                myLocation.getLongitude(),
                zone.getLatitude(),
                zone.getLongitute(),
                result
        );

        float distanceInKm = result[0] * 1000;
        return distanceInKm < zone.getRadius();
    }

    private boolean hasMyNumber(Zone zone, String number) {
        String replacedNumber = number.replace("-", "")
                .replace("+82", "");

        Contact contactInPhone = ContactRepository.getInstance()
                .loadContacts()
                .filter(contact -> contact.getNumber().equals(replacedNumber))
                .singleOrError()
                .onErrorReturnItem(new Contact(null, null))
                .blockingGet();

        for (Contact contact : zone.getContact()) {
            if (contact.getName().equals(contactInPhone.getName())) {
                return true;
            }
        }
        return false;
    }

    private void endCurrentCall(Context context, String number) {
        ITelephony telephonyService;

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Method m = tm.getClass().getDeclaredMethod("getITelephony");

            m.setAccessible(true);
            telephonyService = (ITelephony) m.invoke(tm);

            if ((number != null)) {
                telephonyService.endCall();
                Toast.makeText(context, "전화가 자동으로 수신 거부 되었습니다.: " + number, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "수신거부중 오류가 발생하였습니다. " + number, Toast.LENGTH_SHORT).show();
        }
    }


}
