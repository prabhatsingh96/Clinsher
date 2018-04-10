package com.example.fluper.clinsher.appActivity.controller.model;

/**
 * Created by fluper on 13/3/18.
 */

public class User {


    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String accessToken;
    private String otp;
    private String signupStatus;

    public User() {

    }

    public User(String firstName, String lastName, String email, String password,
                String accessToken, String otp,String signupStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.accessToken = accessToken;
        this.otp = otp;
        this.signupStatus = signupStatus;


    }

    public String getSignupStatus() {
        return signupStatus;
    }

    public void setSignupStatus(String signupStatus) {
        this.signupStatus = signupStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }


}
