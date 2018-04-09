package com.phananh.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by LeNghi on 4/9/2018.
 */

public class LoginResponse {
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    public String getToken() {
        return token;
    }

    public String getFullName() {
        return fullName;
    }
}
