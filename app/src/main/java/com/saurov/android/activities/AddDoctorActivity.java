package com.saurov.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.saurov.android.R;
import com.saurov.android.database.Doctor;
import com.saurov.android.helpers.MySharedPreference;
import com.saurov.android.helpers.SideDrawer;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddDoctorActivity extends Activity {

    private EditText doctorName;
    private EditText doctorSpeciality;
    private EditText doctorEmail;
    private EditText doctorContactNo;
    private EditText doctorAdress;
    private Button saveDoctorButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        SideDrawer.showDrawer(this);
        ButterKnife.bind(this);

        doctorName = (EditText) findViewById(R.id.doctorNameEditText);
        doctorSpeciality = (EditText) findViewById(R.id.doctorSpecialityEditText);
        doctorEmail = (EditText) findViewById(R.id.doctorEmailEditText);
        doctorContactNo = (EditText) findViewById(R.id.doctorContactNoEditText);
        doctorAdress = (EditText) findViewById(R.id.doctorAddressEditText);

        saveDoctorButton = (Button) findViewById(R.id.saveDoctorButton);

    }

    @OnClick(R.id.saveDoctorButton)
    public void saveDoctorInfo(){

        if(doctorName.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Enter Doctor Name", Toast.LENGTH_SHORT).show();
        }else{

            Doctor doctor = new Doctor(doctorName.getText().toString(), MySharedPreference.getCurrentUserId(this));
            doctor.setDoctorEmail(doctorEmail.getText().toString());
            doctor.setAddress(doctorAdress.getText().toString());
            doctor.setContactNumber(doctorContactNo.getText().toString());
            doctor.setSpeciality(doctorSpeciality.getText().toString());

            doctor.save();

            Toast.makeText(this, "Doctor Data Saved!!", Toast.LENGTH_SHORT).show();

            finish();

            startActivity(new Intent(this, DoctorActivity.class));

        }
    }
}
