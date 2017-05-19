package com.saurov.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.saurov.android.R;
import com.saurov.android.database.Medicine;
import com.saurov.android.helpers.SideDrawer;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SideDrawer.showDrawer(this);

        ArrayList<String> medicineList = new ArrayList<String>();

        //Medicine.deleteAll(Medicine.class);

        for (Iterator<Medicine> iter = Medicine.findAll(Medicine.class); iter.hasNext(); ) {
            Medicine element = iter.next();

            medicineList.add(element.getMedicineName());
            //Log.d("---------", element.getMedicineName());
        }

        /**for (Iterator<String> iter= medicineList.iterator();iter.hasNext();){

            String s = iter.next();

            System.out.println(s);
        }**/

        ListAdapter medicineListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,medicineList);

        ListView medicineListView = (ListView) findViewById(R.id.medicineListView);

        medicineListView.setAdapter(medicineListAdapter);
    }
}
