package com.planet.nana.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

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
        if (isInBlackList(number)) {
            endCurrentCall(context, number);
        }
    }

    private boolean isInBlackList(String number) {
        return number.equals("TEST");
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
