package com.saurov.android.database;

import com.orm.SugarRecord;

//Medicine Object Class
public class Medicine extends SugarRecord<Medicine> {

    private String medicineName;
    private String startTime;
    private String startDate;
    private String dayChoice;
    private int reminderTimes;

    //This 3 data are represents time of day when medication will be taken
    //Will dynamically add
    private String takeOneTime;
    private String takeTwoTime;
    private String takeThreeTime;

    //Serious question!! what happens if take time falls between two days of week???
    //Will handle it later :)


    public Medicine() {
    }

    ;

    public Medicine(String medicineName, String startTime, String startDate, String dayChoice) {
        this.medicineName = medicineName;
        this.startTime = startTime;
        this.startDate = startDate;
        this.dayChoice = dayChoice;
    }

    public void setDayChoice(String dayChoice) {
        this.dayChoice = dayChoice;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getReminderTimes() {
        return reminderTimes;
    }

    public void setReminderTimes(int reminderTimes) {
        this.reminderTimes = reminderTimes;
    }

    public void setTakeOneTime(String takeOneTime) {
        this.takeOneTime = takeOneTime;
    }

    public void setTakeTwoTime(String takeTwoTime) {
        this.takeTwoTime = takeTwoTime;
    }

    public void setTakeThreeTime(String takeThreeTime) {
        this.takeThreeTime = takeThreeTime;
    }

    public String getTakeOneTime() {
        return takeOneTime;
    }

    public String getTakeTwoTime() {
        return takeTwoTime;
    }

    public String getTakeThreeTime() {
        return takeThreeTime;
    }
}
