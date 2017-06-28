package com.saurov.android.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.saurov.android.R;
import com.saurov.android.database.Medicine;
import com.saurov.android.dialogs.SelectDaysDialogFragment;
import com.saurov.android.helpers.SideDrawer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddMedicationActivity extends FragmentActivity implements SelectDaysDialogFragment.OnDaySelectionDataPassListener {

    private static final String TIME_FORMAT = "h:mm a";
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    //dayIsChecked is an boolean string for database management that checks RadioGroup Days
    //for checked radiobuttons
    public static String dayIsChecked = "";

    Calendar myCalendar = Calendar.getInstance();
    EditText medicineName;
    EditText startDate;
    EditText startTime;
    RadioGroup radioGroupDays;
    RadioGroup reminderTimes;  ///How many times a day to include reminder

    public static int remainderTimeChoice = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        SideDrawer.showDrawer(this);

        ButterKnife.bind(this);
        startDate = (EditText) findViewById(R.id.startDateEditView);
        startTime = (EditText) findViewById(R.id.startTimeEditView);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

        startDate.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                        imm.hideSoftInputFromWindow(startDate.getWindowToken(), 0);

                        new DatePickerDialog(AddMedicationActivity.this, date, myCalendar
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
                updateTimeLabel();
            }
        };

        startTime.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                        imm.hideSoftInputFromWindow(startDate.getWindowToken(), 0);

                        new TimePickerDialog(AddMedicationActivity.this, time, myCalendar.get(Calendar.HOUR),
                                myCalendar.get(Calendar.MINUTE), false).show();
                    }
                }
        );

        reminderTimes = (RadioGroup) findViewById(R.id.reminderTimesRB);
        radioGroupDays = (RadioGroup) findViewById(R.id.dayChoiceRB);

        reminderTimes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                Log.d("TAG", group.getCheckedRadioButtonId() + " " + checkedId);

                switch (checkedId) {

                    case R.id.onceADayRB:
                        remainderTimeChoice = 1;
                        break;
                    case R.id.twiceADayRB:
                        remainderTimeChoice = 2;
                        break;
                    case R.id.thriceADayRB:
                        remainderTimeChoice = 3;
                        break;
                    default:
                        remainderTimeChoice = -1;
                }
            }
        });

        //This radioGroupHandles Day Selection
        radioGroupDays.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {

                    case R.id.everyDayRB:
                        dayIsChecked = "1111111";
                        break;
                    case R.id.specificDaysOfWeekRB:
                        DialogFragment selectDaysFragment = new SelectDaysDialogFragment();

                        selectDaysFragment.show(getSupportFragmentManager(), "day_choice");
                }
            }
        });
    }


    private void updateDateLabel() {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);

        startDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTimeLabel() {

        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.US);

        startTime.setText(sdf.format(myCalendar.getTime()));
    }


    //Adding medicine to database
    @OnClick(R.id.addMedicineInfo)
    public void addMedicineInfo() {

        medicineName = (EditText) findViewById(R.id.medNameEditView);
        startDate = (EditText) findViewById(R.id.startDateEditView);
        startTime = (EditText) findViewById(R.id.startTimeEditView);

        //String medicineNameString =  medicineName.getText().toString();

        Medicine medicine = new Medicine(medicineName.getText().toString(),
                startTime.getText().toString(), startDate.getText().toString(), dayIsChecked);

        Log.d("TAG", "Choice: " + remainderTimeChoice);

        if (remainderTimeChoice != -1) {
            medicine.setReminderTimes(remainderTimeChoice);
        }

        String startTimeString = startTime.getText().toString().trim();

        if (!startTimeString.isEmpty() && remainderTimeChoice != -1) {
            handleReminderTimesData(startTimeString, medicine);
        }

        medicine.save();

        Toast.makeText(getApplicationContext(), "Medicine Saved!", Toast.LENGTH_LONG).show();

        Log.d("TAG", medicine.getTakeOneTime() + " " + medicine.getTakeTwoTime() + " " + medicine.getTakeThreeTime());

        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public int getRemainderTimeChoice() {
        return remainderTimeChoice;
    }


    //This method is used for auto generating reminder times in days according to remainderTimeChoice
    final public void handleReminderTimesData(String startTimeString, Medicine medicine) {

        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.US);

        Date date;

        try {
            date = sdf.parse(startTimeString);
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "Invalid Time Input", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);


        ///Edit and Add Medicine activity can create problem regarding null values
        ///on take times and startTime

        switch (remainderTimeChoice) {

            case 1:
                medicine.setTakeTwoTime(null);
                medicine.setTakeThreeTime(null);
                medicine.setTakeOneTime(startTimeString);
                break;
            case 2:
                medicine.setTakeTwoTime(null);
                medicine.setTakeThreeTime(null);
                medicine.setTakeOneTime(startTimeString);
                calendar.add(Calendar.HOUR, 12);
                medicine.setTakeTwoTime(sdf.format(calendar.getTime()));
                break;
            case 3:
                medicine.setTakeTwoTime(null);
                medicine.setTakeThreeTime(null);
                medicine.setTakeOneTime(startTimeString);
                calendar.add(Calendar.HOUR, 8);
                medicine.setTakeTwoTime(sdf.format(calendar.getTime()));
                calendar.add(Calendar.HOUR, 8);
                medicine.setTakeThreeTime(sdf.format(calendar.getTime()));
                break;
        }
    }

    @Override
    public void OnDaySelectionDataPass(String daySelection) {
        dayIsChecked = daySelection;
    }
}
