package com.saurov.android.database;

import com.orm.SugarRecord;

public class Doctor extends SugarRecord<Doctor> {

    private String doctorName;
    private String doctorEmail;
    private String speciality;
    private String contactNumber;
    private String address;

    private long userId;

    public Doctor(String doctorName, long userId) {
        this.doctorName = doctorName;
        this.userId = userId;
    }

    public Doctor(String doctorName, String doctorEmail, String speciality, String contactNumber, String address) {
        this.doctorName = doctorName;
        this.doctorEmail = doctorEmail;
        this.speciality = speciality;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Doctor() {
    }

    public long getUserId() {
        return userId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getAddress() {
        return address;
    }
}
