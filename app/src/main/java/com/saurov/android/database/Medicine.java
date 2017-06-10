package com.saurov.android.database;

import com.orm.SugarRecord;

public class Medicine extends SugarRecord<Medicine>{

    private String medicineName;
    private String startTime;
    private String startDate;
    private String dayChoice;

    public Medicine(){};

    public Medicine(String medicineName, String startTime, String startDate, String dayChoice) {
        this.medicineName = medicineName;
        this.startTime = startTime;
        this.startDate = startDate;
        this.dayChoice = dayChoice;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStartDate() {
        return startDate;
    }
}
