package com.phananh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by LeNghi on 5/4/2018.
 */

public class UserProfiles {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("gender")
    @Expose
    private  boolean gender;
    @SerializedName("phone")
    @Expose
    private  String phone;
    @SerializedName("birthday")
    @Expose
    private  String birthday;
    @SerializedName("image")
    @Expose
    private  String image;
    @SerializedName("address")
    @Expose
    private  String address;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public UserProfiles(String fullName, boolean gender, String phone, String address) {
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }
}
