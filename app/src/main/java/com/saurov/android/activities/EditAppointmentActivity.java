package com.saurov.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.saurov.android.CommonConstants;
import com.saurov.android.R;
import com.saurov.android.database.DoctorAppointment;

public class EditAppointmentActivity extends AddAppointmentActivity {

    private DoctorAppointment appointmentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long appointmentId = getIntent().getLongExtra(CommonConstants.ARG_APPOINTMENT_ID, 0);

        appointmentItem = DoctorAppointment.findById(DoctorAppointment.class, appointmentId);

        appointmentName.setText(appointmentItem.getAppointmentTitle());
        appointmentLocation.setText(appointmentItem.getAppointmentLocation());
        appointmentNote.setText(appointmentItem.getAppointmentNotes());
        appointmentTime.setText(appointmentItem.getAppointmentTime());
        appointmentDate.setText(appointmentItem.getAppointmentDate());


        Button editButton = (Button) findViewById(R.id.saveAppointmentButton);

        editButton.setText("Edit");

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (appointmentTime.getText().toString().trim().isEmpty() || appointmentDate.getText().toString().trim().isEmpty()) {

                    Toast.makeText(EditAppointmentActivity.this, "Specify Time and Date", Toast.LENGTH_SHORT).show();
                } else {

                    appointmentItem.setAppointmentDate(appointmentDate.getText().toString());
                    appointmentItem.setAppointmentTime(appointmentTime.getText().toString());
                    appointmentItem.setAppointmentLocation(appointmentLocation.getText().toString());
                    appointmentItem.setAppointmentTitle(appointmentName.getText().toString());
                    appointmentItem.setAppointmentNotes(appointmentNote.getText().toString());

                    appointmentItem.save();

                    Intent i = new Intent(EditAppointmentActivity.this, DoctorDetailActivity.class);

                    i.putExtra(CommonConstants.VIEWPAGER_POSTION, 1);
                    i.putExtra(DoctorDetailFragment.ARG_DOCTOR_ID, appointmentItem.getDoctorId());

                    startActivity(i);

                    finish();
                }
            }
        });
    }
}
