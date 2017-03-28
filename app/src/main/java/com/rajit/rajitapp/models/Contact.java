package com.rajit.rajitapp.models;

/**
 * Created by Nowfel Mashnoor on 3/28/2017.
 */

public class Contact {
    private String name;
    private String phone;
    private String designation;
    private String department;

    public Contact(String name, String phone, String designation, String department) {
        this.name = name;
        this.phone = phone;
        this.designation = designation;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDesignation() {
        return designation;
    }

    public String getDepartment() {
        return department;
    }
}

