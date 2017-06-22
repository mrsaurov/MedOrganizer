package com.saurov.android.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.saurov.android.R;
import com.saurov.android.database.Medicine;
import com.saurov.android.helpers.SideDrawer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddMedicationActivity extends Activity {

    private static final String TIME_FORMAT = "h:mm a";
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    //dayisChecked is an boolean string for database management that checks RadioGroup Days
    //for checked radiobuttons
    public static String dayIsChecked = "";
    //dayID is a array of days from days_popout.xml
    final int dayID[] = {R.id.saturday, R.id.sunday, R.id.monday, R.id.tuesday, R.id.wednesday, R.id.thursday, R.id.friday};

    Calendar myCalendar = Calendar.getInstance();
    EditText medicineName;
    EditText startDate;
    EditText startTime;
    //RadioGroup radioGroupDays;
    RadioGroup reminderTimes;  ///How many times a day to include reminder

    View popUp;
    PopupWindow popupWindow;

    public static int remainderTimeChoice = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        SideDrawer.showDrawer(this);

        ButterKnife.bind(this);
        startDate = (EditText) findViewById(R.id.startDateEditView);
        startTime = (EditText) findViewById(R.id.startTimeEditView);
        //radioGroupDays = (RadioGroup) findViewById(R.id.radioGroupDays);
        //startDate.setEnabled(false);

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

        //Initializing Layout Inflater and PopWindow
        LayoutInflater layoutInflater = getLayoutInflater();

        popUp = layoutInflater.inflate(R.layout.days_popout, null);

        popupWindow = new PopupWindow(popUp, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        //Setting listener for Reminder Times a day


        reminderTimes = (RadioGroup) findViewById(R.id.reminderTimesRB);

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
    }

    private void updateDateLabel() {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);

        startDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTimeLabel() {

        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.US);

        startTime.setText(sdf.format(myCalendar.getTime()));
    }

    //Checking for Days RadioButton selections
    public void onDaysRBClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.specificDaysOfWeekRB:
                if (checked) {

                    final Button saveButton = (Button) popUp.findViewById(R.id.saveButton);

                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (popupWindow.isShowing()) {

                                //Log.d("TAG", "inside Button");

                                String dayChoice = "";

                                for (int i = 0; i < dayID.length; i++) {

                                    CheckBox checkBox = (CheckBox) popUp.findViewById(dayID[i]);

                                    if (checkBox.isChecked())
                                        dayChoice = dayChoice.concat("1");
                                    else
                                        dayChoice = dayChoice.concat("0");
                                }

                                dayIsChecked = dayChoice;
                            }

                            Toast.makeText(getApplicationContext(), dayIsChecked, Toast.LENGTH_SHORT).show();
                            popupWindow.dismiss();
                        }
                    });

                    popupWindow.setFocusable(true);
                    popupWindow.setTouchable(true);

                    popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);

                }
                //Toast.makeText(getApplicationContext(),"Sp",Toast.LENGTH_SHORT).show();
                break;
            case R.id.everyDayRB:
                if (checked) {
                    dayIsChecked = "1111111";
                }
                break;
            default:
                Toast.makeText(getApplicationContext(), "Nothing Is Cheked!", Toast.LENGTH_SHORT).show();
                break;
        }
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
}
