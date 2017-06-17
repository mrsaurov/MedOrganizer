package com.saurov.android.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.saurov.android.R;

//This is the container class for Medicine Detail Fragment
public class MedicineDetailActivity extends FragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);

        Bundle arguments = new Bundle();

        arguments.putLong(MedicineDetailFragment.ARG_MEDICINE_ID,
                getIntent().getLongExtra(MedicineDetailFragment.ARG_MEDICINE_ID,0));

        MedicineDetailFragment medDetailFragment = new MedicineDetailFragment();

        medDetailFragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction().add(R.id.medicine_detail_container,medDetailFragment).commit();
    }
}
