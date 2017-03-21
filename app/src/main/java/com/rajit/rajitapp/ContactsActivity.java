package com.rajit.rajitapp;

import android.app.Activity;
import android.os.Bundle;

public class ContactsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        SideDrawer.showDrawer(this);
    }
}
