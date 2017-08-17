package com.saurov.android.database;

import android.content.Context;

import com.orm.SugarRecord;
import com.saurov.android.helpers.MySharedPreference;

import java.util.Stack;

public class MedicineHistory extends SugarRecord<MedicineHistory> {

    long medicineId;
    long userId;
    String skippedRecords;
    String takenRecords;

    public MedicineHistory() {
    }

    public MedicineHistory(Context context, long medicineId) {

        userId = MySharedPreference.getCurrentUserId(context);
        this.medicineId = medicineId;
    }

    public void addDataToSkippedRecord(String time) {
        skippedRecords= time;
    }

    public void addDataToTakenRecord(String time) {
        takenRecords= time;
    }

    public String getSkippedRecords() {
        return skippedRecords;
    }

    public String getTakenRecords() {
        return takenRecords;
    }

    public long getMedicineId() {
        return medicineId;
    }

    public long getUserId() {
        return userId;
    }
}
