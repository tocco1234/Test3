package com.example.callrandomsms;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.Random;
import java.util.Set;

public class SmsSender {

    public static void sendSmartMessage(Context context, String missedNumber) {
        SharedPreferences prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

        Set<String> savedNumbers = prefs.getStringSet("number_list", null);
        String messageTemplate = prefs.getString("message_text", "تماس شما بی‌پاسخ ماند. لطفاً مجدداً تماس بگیرید: ");
        boolean useRandom = prefs.getBoolean("use_random", true); // true: random, false: index-based

        if (savedNumbers == null || savedNumbers.isEmpty()) return;

        String[] numberArray = savedNumbers.toArray(new String[0]);

        int index;
        if (useRandom) {
            index = new Random().nextInt(numberArray.length);
        } else {
            SharedPreferences callCountPrefs = context.getSharedPreferences("call_counts", Context.MODE_PRIVATE);
            int count = callCountPrefs.getInt(missedNumber, 0) + 1;
            callCountPrefs.edit().putInt(missedNumber, count).apply();

            int listSize = numberArray.length;
            index = (count - 1) % listSize;
        }

        String selected = numberArray[index];
        String finalMessage = messageTemplate + selected;

        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(missedNumber, null, finalMessage, null, null);
            Toast.makeText(context, "SMS ارسال شد به: " + missedNumber, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "خطا در ارسال SMS", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
