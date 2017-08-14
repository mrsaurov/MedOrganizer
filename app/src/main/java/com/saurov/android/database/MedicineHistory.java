package com.saurov.android.database;

import com.orm.SugarRecord;

public class MedicineHistory extends SugarRecord<MedicineHistory> {

    long medicineId;
    long userId;
    String dateAdded;
    String[] skippedRecords;
    String[] takenRecords;
}
