package com.saurov.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.saurov.android.NotificationService;
import com.saurov.android.R;
import com.saurov.android.database.Medicine;
import com.saurov.android.helpers.SideDrawer;

import java.util.ArrayList;
import java.util.Iterator;

//This activity shows Medicine List

public class MainActivity extends Activity {

    ListView medicineListView;
    ArrayAdapter<String> medicineListAdapter;
    ArrayList<String> medicineList = new ArrayList<>();
    ArrayList<Long> medicineId = new ArrayList<>();

    //public static long loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(MainActivity.this, NotificationService.class);
        startService(i);
        //Getting the logged in User id
        //if (getIntent().hasExtra(Login.ARG_USER_ID))
          //  loggedInUserId = getIntent().getLongExtra(Login.ARG_USER_ID, 0);

        SideDrawer.showDrawer(this);

        //Moved ListView Population Logic to onResume
//
//        for (Iterator<Medicine> iter = Medicine.findAll(Medicine.class); iter.hasNext(); ) {
//            Medicine element = iter.next();
//
//            medicineList.add(element.getMedicineName());
//            medicineId.add(element.getId());
//        }


        medicineListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicineList);

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

        //Log.d("TAG", "insideOnResume");

        medicineList.clear();
        medicineId.clear();

        for (Iterator<Medicine> iter = Medicine.findAll(Medicine.class); iter.hasNext(); ) {
            Medicine element = iter.next();

            medicineList.add(element.getMedicineName());
            medicineId.add(element.getId());
        }

        medicineListAdapter.notifyDataSetChanged();
    }
}
