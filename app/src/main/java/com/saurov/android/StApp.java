package com.saurov.android;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.orm.SugarApp;

/**
 * Created by Saurov on 15-May-17.
 */

public class StApp extends SugarApp {

        public void onCreate() {
            super.onCreate();
            Stetho.initializeWithDefaults(this);
        }
}
