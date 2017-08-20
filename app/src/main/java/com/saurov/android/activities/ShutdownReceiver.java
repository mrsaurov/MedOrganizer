package com.saurov.android.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.saurov.android.helpers.MySharedPreference;


public class ShutdownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        MySharedPreference.setAlarmTriggerStatus(context, false);

    }
}
