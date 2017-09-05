package com.saurov.android.database;


import com.orm.SugarRecord;

public class DoctorAppointment extends SugarRecord<DoctorAppointment> {

    private String appointmentDate;
    private String appointmentTime;
    private long userId;
    private long doctorId;
    private String appointmentTitle;
    private String appointmentNotes;

    public DoctorAppointment() {
    }

    public DoctorAppointment(String appointmentDate, String appointmentTime, long userId, long doctorId, String appointmentTitle, String appointmentNotes) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.userId = userId;
        this.doctorId = doctorId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentNotes = appointmentNotes;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public long getUserId() {
        return userId;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public String getAppointmentNotes() {
        return appointmentNotes;
    }


}
