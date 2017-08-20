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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.saurov.android.R;
import com.saurov.android.database.Medicine;
import com.saurov.android.dialogs.SelectDaysDialogFragment;
import com.saurov.android.helpers.CustomTimeListAdapter;
import com.saurov.android.helpers.MySharedPreference;
import com.saurov.android.helpers.SideDrawer;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddMedicationActivity extends FragmentActivity implements SelectDaysDialogFragment.OnDaySelectionDataPassListener {

    public static final String TIME_FORMAT = "h:mm a";
    public static final String DATE_FORMAT = "dd-MM-yyyy";

    //dayIsChecked is an boolean string for database management that checks RadioGroup Days
    //for checked radiobuttons
    public static String dayIsChecked = "";
    public static int remainderTimeChoice = -1;
    Calendar myCalendar = Calendar.getInstance();
    EditText medicineName;
    EditText startDate;
    RadioGroup radioGroupDays;
    RadioGroup reminderTimes;  ///How many times a day to include reminder

    private CustomTimeListAdapter customTimeListAdapter;
    private ListView timePickerList;
    private List<String> timeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        SideDrawer.showDrawer(this);

        ButterKnife.bind(this);
        startDate = (EditText) findViewById(R.id.startDateEditView);

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


        reminderTimes = (RadioGroup) findViewById(R.id.reminderTimesRB);
        radioGroupDays = (RadioGroup) findViewById(R.id.dayChoiceRB);


        //Referencing timePicker ListView
        timePickerList = (ListView) findViewById(R.id.timePickerListView);
        timePickerList.setVisibility(View.GONE);


        reminderTimes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                timeData = Arrays.asList(null, null, null);

                timePickerList.setVisibility(View.VISIBLE);

                switch (checkedId) {
                    case R.id.onceADayRB:
                        remainderTimeChoice = 1;
                        customTimeListAdapter = new CustomTimeListAdapter(AddMedicationActivity.this, 1);
                        timePickerList.setAdapter(customTimeListAdapter);
                        setListViewHeightBasedOnItems(timePickerList);
                        break;
                    case R.id.twiceADayRB:
                        remainderTimeChoice = 2;
                        customTimeListAdapter = new CustomTimeListAdapter(AddMedicationActivity.this, 2);
                        timePickerList.setAdapter(customTimeListAdapter);
                        setListViewHeightBasedOnItems(timePickerList);
                        break;
                    case R.id.thriceADayRB:
                        remainderTimeChoice = 3;
                        customTimeListAdapter = new CustomTimeListAdapter(AddMedicationActivity.this, 3);
                        timePickerList.setAdapter(customTimeListAdapter);
                        setListViewHeightBasedOnItems(timePickerList);
                        break;
                }


                customTimeListAdapter.setChangeButtonClickListener(new CustomTimeListAdapter.ChangeButtonListener() {
                    @Override
                    public void onChangeButtonClickListener(final int position, final CustomTimeListAdapter.TimeHolder timeHolder) {

                        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                                myCalendar.set(Calendar.HOUR, hourOfDay);
                                myCalendar.set(Calendar.MINUTE, minute);

                                SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.US);

                                timeHolder.timeText.setText(sdf.format(myCalendar.getTime()));

                                timeData.set(position, timeHolder.timeText.getText().toString());

                                Log.d("ifd", timeData.toString());
                            }
                        };

                        new TimePickerDialog(AddMedicationActivity.this, time, myCalendar.get(Calendar.HOUR),
                                myCalendar.get(Calendar.MINUTE), false).show();
                    }
                });
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

    ///method for adding timeData to medicineItem...always called from addMedicineInfo()
    public void insertMedicineTakeTimesToDatabase(Medicine medicine) {

        if (timeData != null) {

            Log.d("TAG", timeData.toString());

            medicine.setTimeOneToTakeMed(timeData.get(0));
            medicine.setTimeTwoToTakeMed(timeData.get(1));
            medicine.setTimeTheeToTakeMed(timeData.get(2));
        }
    }

    //Adding medicine to database
    @OnClick(R.id.addMedicineInfo)
    public void addMedicineInfo() {

        medicineName = (EditText) findViewById(R.id.medNameEditView);
        startDate = (EditText) findViewById(R.id.startDateEditView);

        //String medicineNameString =  medicineName.getText().toString();

        Medicine medicine = new Medicine(medicineName.getText().toString(), startDate.getText().toString(), dayIsChecked,
                MySharedPreference.getCurrentUserId(this));

        Log.d("TAG", "Choice: " + remainderTimeChoice);

        if (remainderTimeChoice != -1) {
            medicine.setReminderTimes(remainderTimeChoice);
        }

        //if (!startTimeString.isEmpty() && remainderTimeChoice != -1) {
        //  handleReminderTimesData(startTimeString, medicine);
        //}

        insertMedicineTakeTimesToDatabase(medicine);

        medicine.save();

        Toast.makeText(getApplicationContext(), "Medicine Saved!", Toast.LENGTH_LONG).show();

        //Log.d("TAG", medicine.getTakeOneTime() + " " + medicine.getTakeTwoTime() + " " + medicine.getTakeThreeTime());

        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public int getRemainderTimeChoice() {
        return remainderTimeChoice;
    }



    //Getting the daySelection Data From SelectDaysDialogFragment
    @Override
    public void OnDaySelectionDataPass(String daySelection) {
        dayIsChecked = daySelection;
    }

    //Method for setting list view height dynamically
    public boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }
    }

}
