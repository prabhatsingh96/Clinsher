package com.example.fluper.clinsher.appActivity.controller.utils;

/**
 * Created by fluper on 9/3/18.
 */

public class MyJobUserInfo {

    private String name;
    private String job;
    private String location;
    private String address;

    public MyJobUserInfo() {
    }

    public MyJobUserInfo(String name, String job, String location, String address) {
        this.name = name;
        this.job = job;
        this.location = location;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
