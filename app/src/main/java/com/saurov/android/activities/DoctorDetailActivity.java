package com.saurov.android.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.saurov.android.R;
import com.saurov.android.helpers.SideDrawer;

public class DoctorDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        SideDrawer.showDrawer(this);

        Bundle arguments = new Bundle();

        arguments.putLong(DoctorDetailFragment.ARG_DOCTOR_ID,
                getIntent().getLongExtra(DoctorDetailFragment.ARG_DOCTOR_ID, 0));

        DoctorDetailFragment doctorDetailFragment = new DoctorDetailFragment();

        doctorDetailFragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction().add(R.id.doctor_detail_container, doctorDetailFragment).commit();


    }
}
