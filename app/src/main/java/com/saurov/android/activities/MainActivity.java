package com.saurov.android.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.saurov.android.R;
import com.saurov.android.database.Medicine;
import com.saurov.android.dialogs.DeleteMedicineDialogFragment;
import com.saurov.android.helpers.CustomMedicineListAdapter;
import com.saurov.android.helpers.MySharedPreference;
import com.saurov.android.helpers.NotificationIntentService;
import com.saurov.android.helpers.SideDrawer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

//This activity shows Medicine List

public class MainActivity extends FragmentActivity {

    ListView medicineListView;
    CustomMedicineListAdapter medicineListAdapter;
    ArrayList<String> medicineList = new ArrayList<>();
    ArrayList<Long> medicineId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SideDrawer.showDrawer(this);

        FloatingActionButton addMedicationFAB = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        addMedicationFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddMedicationActivity.class);

                startActivity(i);
            }
        });


        ///Firing up the notification service

//        Intent i = new Intent(MainActivity.this, NotificationService.class);
//        startService(i);


        Log.d("TAG", "alarm status: " + MySharedPreference.isAlarmTriggered(this));

        //if (!MySharedPreference.isAlarmTriggered(this)) {

        Log.d("TAG", "service starter");

        Intent myIntent = new Intent(this, NotificationIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //calendar.add(Calendar.SECOND, 60); // first time
        long frequency = 60 * 1000; // in ms
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pendingIntent);

        MySharedPreference.setAlarmTriggerStatus(this, true);
        //}


        //Moved ListView Population Logic to onResume
//
//        for (Iterator<Medicine> iter = Medicine.findAll(Medicine.class); iter.hasNext(); ) {
//            Medicine element = iter.next();
//
//            medicineList.add(element.getMedicineName());
//            medicineId.add(element.getId());
//        }

        medicineListAdapter = new CustomMedicineListAdapter(this, medicineList);

        medicineListView = (ListView) findViewById(R.id.medicineListView);

        medicineListView.setAdapter(medicineListAdapter);

        //Setting up listener for medicine Items
        medicineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //String name = names.get(position);

                long medId = medicineId.get(position);

                Context context = view.getContext();
                Intent intent = new Intent(context, MedicineDetailActivity.class);
                //Passing Data to Fragment Activity
                intent.putExtra(MedicineDetailFragment.ARG_MEDICINE_ID, medId);

                context.startActivity(intent);
            }
        });

        registerForContextMenu(medicineListView);

    }

    @Override
    protected void onResume() {
        super.onResume();

        medicineList.clear();
        medicineId.clear();

        TextView userMessage = (TextView) findViewById(R.id.userMessageMedicine);

        for (Iterator<Medicine> iter = Medicine.findAll(Medicine.class); iter.hasNext(); ) {
            Medicine element = iter.next();

            if (element.getUserId() == MySharedPreference.getCurrentUserId(this)) {
                medicineList.add(element.getMedicineName());
                medicineId.add(element.getId());
            }
        }

        if(medicineList.isEmpty()){
            userMessage.setVisibility(View.VISIBLE);
        }

        medicineListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "History");
        menu.add(0, v.getId(), 1, "Delete");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getTitle() == "Delete") {
            DialogFragment deleteDialogFragment = new DeleteMedicineDialogFragment();

            Bundle arguments = new Bundle();

            arguments.putLong(MedicineDetailFragment.ARG_MEDICINE_ID, medicineId.get(info.position));

            deleteDialogFragment.setArguments(arguments);

            deleteDialogFragment.show(getSupportFragmentManager(), "delete medicine");
        } else if (item.getTitle() == "Edit") {

            Intent i = new Intent(this, EditMedicineActivity.class);

            i.putExtra(MedicineDetailFragment.ARG_MEDICINE_ID, medicineId.get(info.position));

            startActivity(i);

        } else if (item.getTitle() == "History") {

            Intent intent = new Intent(this, MedicineHistoryActivity.class);

            intent.putExtra(MedicineDetailFragment.ARG_MEDICINE_ID, medicineId.get(info.position));

            startActivity(intent);
        }

        return true;
    }
}




