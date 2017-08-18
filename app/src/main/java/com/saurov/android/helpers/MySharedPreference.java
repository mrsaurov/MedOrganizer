package com.saurov.android.helpers;

import android.content.Context;
import android.content.SharedPreferences;

//This class is used to handle userID shared preference
public class MySharedPreference {

    private static final String USER_FILE_NAME = "user_info";
    private static final String ALARM_FILE_NAME = "alarm_info";
    private static final String ARG_USER_ID = "user_id";
    private static final String ARG_ALARM_STATUS = "alarm_status";
    private static SharedPreferences userSharedPreference;
    private static SharedPreferences alarmStatusSharedPreference;
    private static SharedPreferences.Editor editor;

    public static boolean isAlarmTriggered(Context context) {

        alarmStatusSharedPreference = context.getSharedPreferences(ALARM_FILE_NAME, Context.MODE_PRIVATE);

        return alarmStatusSharedPreference.getBoolean(ARG_ALARM_STATUS, false);
    }

    public static void setAlarmTriggerStatus(Context context, boolean status) {

        alarmStatusSharedPreference = context.getSharedPreferences(ALARM_FILE_NAME, Context.MODE_PRIVATE);

        editor = alarmStatusSharedPreference.edit();

        editor.putBoolean(ARG_ALARM_STATUS, status);

        editor.apply();

    }


    public static long getCurrentUserId(Context context) {

        userSharedPreference = context.getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE);

        return userSharedPreference.getLong(ARG_USER_ID, -1);
    }

    public static void setCurrentUserId(Context context,long id) {

        userSharedPreference = context.getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE);
        editor = userSharedPreference.edit();

        editor.putLong(ARG_USER_ID, id);

        editor.apply();
    }
}
