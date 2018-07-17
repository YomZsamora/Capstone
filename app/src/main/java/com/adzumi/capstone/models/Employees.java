package com.adzumi.capstone.models;


import org.parceler.Parcel;

@Parcel
public class Employees {
    private String name;
    private String department;
    private String phone_number;
    private String pushId;

    public Employees() {}

    public Employees(String name, String department, String phone_number){
        this.name = name;
        this.department = department;
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return department;
    }

    public String getPhone() {
        return phone_number;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

}
