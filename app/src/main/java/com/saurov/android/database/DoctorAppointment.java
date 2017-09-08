package com.saurov.android.database;


import com.orm.SugarRecord;

public class DoctorAppointment extends SugarRecord<DoctorAppointment> {

    private String appointmentDate;
    private String appointmentTime;
    private long userId;
    private long doctorId;
    private String appointmentTitle;
    private String appointmentNotes;
    private String appointmentLocation;

    public DoctorAppointment() {
    }

    public DoctorAppointment(String appointmentDate, String appointmentTime,
                             long userId, long doctorId, String appointmentTitle, String appointmentNotes, String appointmentLocation) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.userId = userId;
        this.doctorId = doctorId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentNotes = appointmentNotes;
        this.appointmentLocation = appointmentLocation;
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

    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public void setAppointmentNotes(String appointmentNotes) {
        this.appointmentNotes = appointmentNotes;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }
}
