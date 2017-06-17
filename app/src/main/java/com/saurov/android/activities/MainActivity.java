package com.saurov.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.saurov.android.R;
import com.saurov.android.database.Medicine;
import com.saurov.android.helpers.SideDrawer;

import java.util.ArrayList;
import java.util.Iterator;

//This activity shows Medicine List

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SideDrawer.showDrawer(this);

         final ArrayList<String> medicineList = new ArrayList<String>();
         final ArrayList<Long> medicineId = new ArrayList<Long>();

        //Medicine.deleteAll(Medicine.class);

        for (Iterator<Medicine> iter = Medicine.findAll(Medicine.class); iter.hasNext();) {
            Medicine element = iter.next();

            medicineList.add(element.getMedicineName());
            medicineId.add(element.getId());
        }


        ListAdapter medicineListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,medicineList);

        ListView medicineListView = (ListView) findViewById(R.id.medicineListView);

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
}
