package com.saurov.android.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.saurov.android.R;
import com.saurov.android.database.Doctor;

public class EditDoctorActivity extends AddDoctorActivity {

    private Doctor doctorItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final long doctorId = getIntent().getLongExtra(DoctorDetailFragment.ARG_DOCTOR_ID, 0);

        doctorItem = Doctor.findById(Doctor.class, doctorId);

        Log.d("DOC", doctorId + "doc id" + doctorItem.getDoctorEmail());

        doctorName.setText(doctorItem.getDoctorName());
        doctorSpeciality.setText(doctorItem.getSpeciality());
        doctorEmail.setText(doctorItem.getDoctorEmail());
        doctorContactNo.setText(doctorItem.getContactNumber());
        doctorAdress.setText(doctorItem.getAddress());

        Button editDoctorButton = (Button) findViewById(R.id.saveDoctorButton);
        TextView titleText = (TextView) findViewById(R.id.addDoctorTitle);
        titleText.setText("Edit Doctor");

        editDoctorButton.setText("EDIT");

        editDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (doctorName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(EditDoctorActivity.this, "Enter Doctor Name", Toast.LENGTH_SHORT).show();
                } else {

                    doctorItem.setDoctorName(doctorName.getText().toString());
                    doctorItem.setDoctorEmail(doctorEmail.getText().toString());
                    doctorItem.setAddress(doctorAdress.getText().toString());
                    doctorItem.setContactNumber(doctorContactNo.getText().toString());
                    doctorItem.setSpeciality(doctorSpeciality.getText().toString());

                    doctorItem.save();

                    Toast.makeText(EditDoctorActivity.this, "Doctor Data Edited!!", Toast.LENGTH_SHORT).show();

                    finish();

                    startActivity(new Intent(EditDoctorActivity.this, DoctorActivity.class));

                }
            }
        });
    }
}

