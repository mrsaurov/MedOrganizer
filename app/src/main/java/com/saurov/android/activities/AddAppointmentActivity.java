package com.saurov.android.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.saurov.android.CommonConstants;
import com.saurov.android.R;
import com.saurov.android.database.Doctor;
import com.saurov.android.database.DoctorAppointment;
import com.saurov.android.helpers.MySharedPreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAppointmentActivity extends Activity {

    private long doctorId;

    protected EditText appointmentName;
    protected EditText appointmentLocation;
    protected EditText appointmentNote;
    protected TextView appointmentTime;
    protected TextView appointmentDate;
    protected Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);


        ButterKnife.bind(this);

        appointmentName = (EditText) findViewById(R.id.appointmentNameEditText);
        appointmentLocation = (EditText) findViewById(R.id.appointmentLocationEditText);
        appointmentNote = (EditText) findViewById(R.id.appointmentNoteEditText);
        appointmentTime = (TextView) findViewById(R.id.appointmentTimeTextView);
        appointmentDate = (TextView) findViewById(R.id.appointmentDateTextView);

        doctorId = getIntent().getLongExtra(DoctorDetailFragment.ARG_DOCTOR_ID, 0);


        appointmentLocation.setText(Doctor.findById(Doctor.class, doctorId).getAddress());



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat(AddMedicationActivity.DATE_FORMAT, Locale.US);

                appointmentDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        appointmentDate.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        new DatePickerDialog(AddAppointmentActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                }
        );

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                myCalendar.set(Calendar.HOUR, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat sdf = new SimpleDateFormat(AddMedicationActivity.TIME_FORMAT, Locale.US);

                appointmentTime.setText(sdf.format(myCalendar.getTime()));
            }
        };

        appointmentTime.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {


                        new TimePickerDialog(AddAppointmentActivity.this, time, myCalendar.get(Calendar.HOUR),
                                myCalendar.get(Calendar.MINUTE), false).show();
                    }
                }

        );

    }

    @OnClick(R.id.saveAppointmentButton)
    public void saveAppointment() {

        if (appointmentTime.getText().toString().trim().isEmpty() || appointmentDate.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "Specify Time and Date", Toast.LENGTH_SHORT).show();
        } else {

            DoctorAppointment appointment = new DoctorAppointment(appointmentDate.getText().toString(),
                    appointmentTime.getText().toString(), MySharedPreference.getCurrentUserId(getApplicationContext()),
                    doctorId, appointmentName.getText().toString(), appointmentNote.getText().toString(),appointmentLocation.getText().toString());

            appointment.save();

            Intent i = new Intent(this, DoctorDetailActivity.class);

            i.putExtra(CommonConstants.VIEWPAGER_POSTION, 1);
            i.putExtra(DoctorDetailFragment.ARG_DOCTOR_ID,appointment.getDoctorId());

            startActivity(i);

            finish();

        }
    }
}
