package com.saurov.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.saurov.android.R;
import com.saurov.android.helpers.SideDrawer;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MedicationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Medication");
        setContentView(R.layout.activity_medication);
        ButterKnife.bind(this);
        SideDrawer.showDrawer(this);
    }

    @OnClick(R.id.addMedicationButton)
    public void add(){

        Intent i= new Intent(this,AddMedicationActivity.class);

        startActivity(i);
    }
}
