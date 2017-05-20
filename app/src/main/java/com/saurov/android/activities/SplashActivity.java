package com.saurov.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class SplashActivity extends Activity {
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent i = new Intent(this, Login.class);

        synchronized (this){
            try {
                wait(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        startActivity(i);

        finish();
    }
}