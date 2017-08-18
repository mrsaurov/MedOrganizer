package com.saurov.android;

import com.facebook.stetho.Stetho;
import com.orm.SugarApp;


public class StApp extends SugarApp {

        public void onCreate() {
            super.onCreate();

            Stetho.initializeWithDefaults(this);
        }
}
