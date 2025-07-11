package com.example.callrandomsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import java.util.Objects;

public class CallReceiver extends BroadcastReceiver {

    private static String lastState = "";
    private static String incomingNumber = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

        if (state == null) return;

        if (Objects.equals(state, TelephonyManager.EXTRA_STATE_RINGING)) {
            incomingNumber = number;
            lastState = "RINGING";
        } else if (Objects.equals(state, TelephonyManager.EXTRA_STATE_IDLE)) {
            if (Objects.equals(lastState, "RINGING") && incomingNumber != null) {
                SmsSender.sendSmartMessage(context, incomingNumber);
            }
            lastState = "IDLE";
        }
    }
}
