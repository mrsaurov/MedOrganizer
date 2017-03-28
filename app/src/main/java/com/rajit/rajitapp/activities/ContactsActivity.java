package com.rajit.rajitapp.activities;

import android.app.Activity;
import android.os.Bundle;

import com.rajit.rajitapp.R;
import com.rajit.rajitapp.helpers.SideDrawer;

public class ContactsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        SideDrawer.showDrawer(this);
    }
}
