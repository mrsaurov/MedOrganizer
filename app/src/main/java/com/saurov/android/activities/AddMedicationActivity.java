package com.saurov.android.activities;

import android.app.Activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.saurov.android.R;
import com.saurov.android.database.Medicine;
import com.saurov.android.helpers.SideDrawer;
import com.wdullaer.materialdatetimepicker.*;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddMedicationActivity extends Activity {

    Calendar myCalendar = Calendar.getInstance();
    EditText medicineName;
    EditText startDate;
    Button addMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        SideDrawer.showDrawer(this);

        ButterKnife.bind(this);
        startDate = (EditText)findViewById(R.id.startDateEditView);
        //startDate.setEnabled(false);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        startDate.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                        imm.hideSoftInputFromWindow(startDate.getWindowToken(), 0);

                        new DatePickerDialog(AddMedicationActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                }
        );
    }

    private void updateLabel(){

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startDate.setText(sdf.format(myCalendar.getTime()));
    }



    @OnClick(R.id.addMedicineInfo)
    public void addMedicineInfo(){

        medicineName = (EditText) findViewById(R.id.medNameEditView);

        String medicineNameString =  medicineName.getText().toString();

        Medicine medicine = new Medicine(medicineNameString);
        medicine.save();
        Toast.makeText(getApplicationContext(), "Saved Medicine!", Toast.LENGTH_LONG).show();
    }

}
