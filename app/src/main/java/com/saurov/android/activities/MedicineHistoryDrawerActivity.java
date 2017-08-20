package com.saurov.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.saurov.android.R;
import com.saurov.android.database.Medicine;
import com.saurov.android.helpers.CustomMedicineListAdapter;
import com.saurov.android.helpers.MySharedPreference;

import java.util.ArrayList;
import java.util.Iterator;

public class MedicineHistoryDrawerActivity extends Activity {

    private ArrayList<String> medicineList = new ArrayList<>();
    private ArrayList<Long> medicineId = new ArrayList<>();
    private CustomMedicineListAdapter medicineHistoryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_history_drawer);

        ListView medicineHistoryListView = (ListView) findViewById(R.id.medicineHistoryListView);

        medicineHistoryListAdapter = new CustomMedicineListAdapter(this, medicineList);

        medicineHistoryListView.setAdapter(medicineHistoryListAdapter);

        medicineHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                long medId = medicineId.get(position);

                Context context = view.getContext();
                Intent intent = new Intent(context, MedicineHistoryActivity.class);
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

        medicineHistoryListAdapter.notifyDataSetChanged();
    }
}
