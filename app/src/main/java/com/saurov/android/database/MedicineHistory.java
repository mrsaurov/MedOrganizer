package com.saurov.android.database;

import android.content.Context;

import com.orm.SugarRecord;
import com.saurov.android.helpers.MySharedPreference;

import java.util.Stack;

public class MedicineHistory extends SugarRecord<MedicineHistory> {

    long medicineId;
    long userId;
    String dateAdded;
    Stack<String> skippedRecords;
    Stack<String> takenRecords;

    public MedicineHistory(Context context, long medicineId, String dateAdded) {

        userId = MySharedPreference.getCurrentUserId(context);
        this.dateAdded = dateAdded;
        skippedRecords = new Stack<>();
        takenRecords = new Stack<>();
    }

    public void addDataToSkippedRecord(String time) {
        skippedRecords.push(time);
    }

    public void addDataToTakenRecord(String time) {
        takenRecords.push(time);
    }

}
