package com.adzumi.capstone.models;


import org.parceler.Parcel;

@Parcel
public class Employees {
    private String name;
    private String dept;
    private String phone_number;
    private String rating;
    private String ratings;
    private boolean isSelected = false;
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

    public String getRating() { return rating; }

    public String getRatings() { return ratings; }

    public String getPhone() {
        return phone_number;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

}
