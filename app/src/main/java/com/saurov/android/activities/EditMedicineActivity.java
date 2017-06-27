package com.saurov.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.saurov.android.R;
import com.saurov.android.database.Medicine;


//This class inherits from AddMedicationActivity and is called from medicine detail fragment for editing Medicine Data
public class EditMedicineActivity extends AddMedicationActivity {

    private Medicine medicineItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button editMedicineInfo = (Button) findViewById(R.id.addMedicineInfo);
        editMedicineInfo.setText("EDIT");

        long medicineId = getIntent().getLongExtra(MedicineDetailFragment.ARG_MEDICINE_ID, 0);

        medicineItem = Medicine.findById(Medicine.class, medicineId);

        medicineName = (EditText) findViewById(R.id.medNameEditView);
        medicineName.setText(medicineItem.getMedicineName());

        editMedicineInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText medicineName = (EditText) findViewById(R.id.medNameEditView);
                EditText startDate = (EditText) findViewById(R.id.startDateEditView);
                EditText startTime = (EditText) findViewById(R.id.startTimeEditView);


                medicineItem.setMedicineName(medicineName.getText().toString());
                medicineItem.setStartDate(startDate.getText().toString());
                medicineItem.setStartTime(startTime.getText().toString());
                medicineItem.setDayChoice(dayIsChecked);

                if (remainderTimeChoice != -1) {
                    medicineItem.setReminderTimes(getRemainderTimeChoice());
                }

                String startTimeString = startTime.getText().toString().trim();


                ///Edit and Add Medicine activity can create problem regarding null values
                ///on take times and startTime

                if (!startTimeString.isEmpty() && remainderTimeChoice != -1) {
                    handleReminderTimesData(startTimeString, medicineItem);
                }


                medicineItem.save();

                Log.d("TAG", "Edit reminderTimes: " + medicineItem.getReminderTimes());

                Intent i = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(i);

                finish();
            }
        });
    }
}

