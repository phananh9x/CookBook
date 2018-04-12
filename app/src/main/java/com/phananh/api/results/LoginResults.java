package com.phananh.api.results;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.phananh.api.response.LoginResponse;
import com.phananh.api.response.BaseResponse;

/**
 * Created by CAOPHUONGDUC on 4/12/2018.
 */

public class LoginResults extends BaseResponse {
    @SerializedName("results")
    @Expose
    public LoginResponse LoginResponse;

    public com.phananh.api.response.LoginResponse getLoginResponse() {
        return LoginResponse;
    }

}
