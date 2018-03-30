package com.example.fluper.clinsher.appActivity.controller.retrofit;

import com.example.fluper.clinsher.appActivity.controller.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by fluper on 13/3/18.
 */

public class ServerResponse {
    @SerializedName("response")
    public User user;

    @SerializedName("message")
    public String message;

    @SerializedName ("cityList")
    public ArrayList<String> cityList;
}
