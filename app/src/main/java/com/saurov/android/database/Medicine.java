package com.saurov.android.database;

import com.orm.SugarRecord;

public class Medicine extends SugarRecord<Medicine>{

    private String medicineName;
    private String startTime;
    private String startDate;

    public Medicine(){};

    public Medicine(String medicineName, String startTime, String startDate) {
        this.medicineName = medicineName;
        this.startTime = startTime;
        this.startDate = startDate;
    }

    public String getMedicineName() {
        return medicineName;
    }
}
