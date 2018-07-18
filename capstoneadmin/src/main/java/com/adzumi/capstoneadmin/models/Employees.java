package com.adzumi.capstoneadmin.models;

import org.parceler.Parcel;

@Parcel
public class Employees {
    private String name;
    private String dept;
    private String phone_number;
    private String pushId;

    public Employees() {}

    public Employees(String name, String dept, String phone_number){
        this.name = name;
        this.dept = dept;
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
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
