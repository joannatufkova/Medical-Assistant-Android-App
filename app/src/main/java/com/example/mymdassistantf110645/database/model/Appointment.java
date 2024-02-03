package com.example.mymdassistantf110645.database.model;

public class Appointment {
    private String doctorName;
    private String speciality;
    private String date;
    private String time;
    private String note;
    private String type;

    // Constructor
    public Appointment(String doctorName, String speciality, String date, String time, String note) {
        this.doctorName = doctorName;
        this.speciality = speciality;
        this.date = date;
        this.time = time;
        this.note = note;
    }

    // Getters and Setters
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
