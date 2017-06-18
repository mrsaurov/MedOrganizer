package com.saurov.android.database;

import com.orm.SugarRecord;

//Medicine Object Class
public class Medicine extends SugarRecord<Medicine>{

    private String medicineName;
    private String startTime;
    private String startDate;
    private String dayChoice;
    private int reminderTimes;

    public Medicine(){};

    public Medicine(String medicineName, String startTime, String startDate, String dayChoice) {
        this.medicineName = medicineName;
        this.startTime = startTime;
        this.startDate = startDate;
        this.dayChoice = dayChoice;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setDayChoice(String dayChoice) {
        this.dayChoice = dayChoice;
    }

    public void setReminderTimes(int reminderTimes) {
        this.reminderTimes = reminderTimes;
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

    public int getReminderTimes() {
        return reminderTimes;
    }
}
