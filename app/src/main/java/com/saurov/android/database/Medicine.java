package com.saurov.android.database;

import com.orm.SugarRecord;

public class Medicine extends SugarRecord<Medicine>{

    private String medicineName;

    public Medicine(){};

    public Medicine(String name){

        this.medicineName = name;
    }

    public String getMedicineName() {
        return medicineName;
    }
}
