package com.saurov.android.helpers;

import android.content.Context;
import android.content.SharedPreferences;

//This class is used to handle userID shared preference
public class MySharedPreference {

    private static final String USER_FILE_NAME = "user_info";
    private static final String ARG_USER_ID = "user_id";
    private static SharedPreferences userSharedPreference;
    private static SharedPreferences.Editor editor;

//    public MySharedPreference(Context context) {
//        userSharedPreference = context.getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE);
//        editor = userSharedPreference.edit();
//    }
//
//    public MySharedPreference(Context context, long id) {
//
//        userSharedPreference = context.getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE);
//        editor = userSharedPreference.edit();
//
//        editor.putLong(ARG_USER_ID, id);
//
//        editor.apply();
//    }


    public static long getCurrentUserId(Context context) {

        userSharedPreference = context.getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE);
        //editor = userSharedPreference.edit();
        return userSharedPreference.getLong(ARG_USER_ID, -1);
    }

    public static void setCurrentUserId(Context context,long id) {

        userSharedPreference = context.getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE);
        editor = userSharedPreference.edit();

        editor.putLong(ARG_USER_ID, id);

        editor.apply();
    }
}