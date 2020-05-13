package com.example.miniproject.model;

import com.google.gson.annotations.SerializedName;

public class loginResponse {

    @SerializedName("status")
    private Boolean status;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("unique_id")
    private String unique_id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
