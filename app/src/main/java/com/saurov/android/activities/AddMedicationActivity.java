package com.saurov.android.activities;

import android.app.Activity;
import android.os.Bundle;

import com.saurov.android.R;
import com.saurov.android.helpers.SideDrawer;


public class AddMedicationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        SideDrawer.showDrawer(this);

    }
}