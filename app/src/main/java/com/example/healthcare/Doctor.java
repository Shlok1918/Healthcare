package com.example.healthcare;

import android.graphics.Bitmap;

public class Doctor {
    private String name;
    private String specialization;
    private String days;
    private String app_time;
    private String phone;
    private Bitmap img;

    public Doctor(String name, String specialization, String days, String app_time, String phone, Bitmap img) {
        this.name = name;
        this.specialization = specialization;
        this.days = days;
        this.app_time = app_time;
        this.phone = phone;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getAppTime() {
        return app_time;
    }

    public void setAppTime(String app_time) {
        this.app_time = app_time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
