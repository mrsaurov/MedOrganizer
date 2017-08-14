package com.saurov.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.saurov.android.R;
import com.saurov.android.database.Medicine;
import com.saurov.android.helpers.CustomMedicineListAdapter;
import com.saurov.android.helpers.MySharedPreference;
import com.saurov.android.helpers.NotificationService;
import com.saurov.android.helpers.SideDrawer;

import java.util.ArrayList;
import java.util.Iterator;

//This activity shows Medicine List

public class MainActivity extends Activity {

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

        Intent i = new Intent(MainActivity.this, NotificationService.class);
        startService(i);


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
    }

    @Override
    protected void onResume() {
        super.onResume();

        medicineList.clear();
        medicineId.clear();

        for (Iterator<Medicine> iter = Medicine.findAll(Medicine.class); iter.hasNext(); ) {
            Medicine element = iter.next();

            if (element.getUserId() == MySharedPreference.getCurrentUserId(this)) {
                medicineList.add(element.getMedicineName());
                medicineId.add(element.getId());
            }
        }

        medicineListAdapter.notifyDataSetChanged();
    }
}
