package com.saurov.android.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.saurov.android.R;
import com.saurov.android.helpers.DoctorDetailsPager;
import com.saurov.android.helpers.SideDrawer;

public class DoctorDetailActivity extends FragmentActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        SideDrawer.showDrawer(this);

        Bundle arguments = new Bundle();

        arguments.putLong(DoctorDetailFragment.ARG_DOCTOR_ID,
                getIntent().getLongExtra(DoctorDetailFragment.ARG_DOCTOR_ID, 0));


        tabLayout = (TabLayout) findViewById(R.id.tabLayoutDoctorDetails);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pagerDoctorDetails);

        DoctorDetailsPager adapter = new DoctorDetailsPager(getSupportFragmentManager(), arguments, 2);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}
