package com.example.musicplayer1.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserResponse implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("password")
    private String password;

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
