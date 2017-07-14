package com.saurov.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.saurov.android.R;
import com.saurov.android.database.Doctor;
import com.saurov.android.helpers.CustomDoctorListAdapter;
import com.saurov.android.helpers.CustomMedicineListAdapter;
import com.saurov.android.helpers.MySharedPreference;
import com.saurov.android.helpers.SideDrawer;

import java.util.ArrayList;
import java.util.Iterator;

public class DoctorActivity extends Activity {

    ListView doctorListView;
    CustomDoctorListAdapter doctorListAdapter;
    ArrayList<String> doctorList = new ArrayList<>();
    ArrayList<Long> doctorId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        SideDrawer.showDrawer(this);

        FloatingActionButton addDoctorFAB = (FloatingActionButton) findViewById(R.id.addDoctorFab);

        addDoctorFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorActivity.this, AddDoctorActivity.class);

                startActivity(i);
            }
        });


        doctorListAdapter = new CustomDoctorListAdapter(this, doctorList);

        doctorListView = (ListView) findViewById(R.id.doctorListView);

        doctorListView.setAdapter(doctorListAdapter);

        //Setting up listener for doctor Items
        doctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                long docId = doctorId.get(position);

                Context context = view.getContext();

                Intent intent = new Intent(context, DoctorDetailActivity.class);
                //Passing Data to Fragment Activity
                intent.putExtra(DoctorDetailFragment.ARG_DOCTOR_ID, docId);

                context.startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        doctorList.clear();
        doctorId.clear();

        for (Iterator<Doctor> iter = Doctor.findAll(Doctor.class); iter.hasNext(); ) {

            Doctor element = iter.next();

            if (element.getUserId() == MySharedPreference.getCurrentUserId(this)) {
                doctorList.add(element.getDoctorName());
                doctorId.add(element.getId());
            }
        }

        doctorListAdapter.notifyDataSetChanged();
    }
}
