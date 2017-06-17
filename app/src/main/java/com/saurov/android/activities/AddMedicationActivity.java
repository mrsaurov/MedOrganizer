package com.saurov.android.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddMedicationActivity extends Activity {

    //dayisChecked is an boolean string for database management that checks RadioGroup Days
    //for checked radiobuttons
    private static String dayIsChecked = "";
    //dayID is a array of days from days_popout.xml
    final int dayID[] = {R.id.saturday, R.id.sunday, R.id.monday, R.id.tuesday, R.id.thursday, R.id.friday};

    Calendar myCalendar = Calendar.getInstance();
    EditText medicineName;
    EditText startDate;
    EditText startTime;
    RadioGroup radioGroupDays;

    View popUp;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        SideDrawer.showDrawer(this);

        ButterKnife.bind(this);
        startDate = (EditText) findViewById(R.id.startDateEditView);
        startTime = (EditText) findViewById(R.id.startTimeEditView);
        radioGroupDays = (RadioGroup) findViewById(R.id.radioGroupDays);
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

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                myCalendar.set(Calendar.HOUR, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTimeLabel();
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

        //Initializing Layout Inflator and PopWindow
        LayoutInflater layoutInflater = getLayoutInflater();

        popUp = layoutInflater.inflate(R.layout.days_popout, null);

        popupWindow = new PopupWindow(popUp, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

    }

    private void updateDateLabel() {

        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTimeLabel() {

        String myFormat = "h:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

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

                                Log.d("TAG", "inside Button");

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
                startTime.getText().toString(), startDate.getText().toString(),dayIsChecked);

        medicine.save();

        Toast.makeText(getApplicationContext(), "Medicine Saved!", Toast.LENGTH_LONG).show();


        finish();
        startActivity(new Intent(this,MainActivity.class));

    }

}
