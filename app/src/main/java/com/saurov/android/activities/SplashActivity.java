package com.saurov.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

//This activity is used for Splash Screen and is the launcher activity of this app
public class SplashActivity extends Activity {
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent i = new Intent(this, Login.class);

       /** synchronized (this){

            try {
                wait(2000);
            } catch (InterruptedException e) {
                startActivity(i);
            }
        }**/

        startActivity(i);

        finish();
    }
}
