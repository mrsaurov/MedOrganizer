package com.saurov.android.activities;

import android.app.Activity;
import android.os.Bundle;

import com.rajit.android.R;
import com.saurov.android.helpers.SideDrawer;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SideDrawer.showDrawer(this);

    }
}
