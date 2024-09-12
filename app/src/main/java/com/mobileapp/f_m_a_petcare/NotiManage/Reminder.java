package com.mobileapp.f_m_a_petcare.NotiManage;

public class Reminder {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
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

    public String getReminderType() {
        return reminderType;
    }

    public void setReminderType(String reminderType) {
        this.reminderType = reminderType;
    }

    private long id;
    private String petId;
    private String date;
    private String time;
    private String reminderType;

    public Reminder(long id, String petId, String date, String time, String reminderType) {
        this.id = id;
        this.petId = petId;
        this.date = date;
        this.time = time;
        this.reminderType = reminderType;
    }


}