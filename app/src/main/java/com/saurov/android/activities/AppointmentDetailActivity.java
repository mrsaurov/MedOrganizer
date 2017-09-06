package com.saurov.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.saurov.android.CommonConstants;
import com.saurov.android.R;
import com.saurov.android.helpers.SideDrawer;

public class AppointmentDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);
        SideDrawer.showDrawer(this);

        Bundle arguments = new Bundle();

        arguments.putLong(CommonConstants.ARG_APPOINTMENT_ID,
                getIntent().getLongExtra(CommonConstants.ARG_APPOINTMENT_ID,0));

        AppointmentDetailFragment fragment = new AppointmentDetailFragment();

        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction().add(R.id.appointment_detail_container,fragment).commit();

    }
}
