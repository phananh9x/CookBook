package com.phananh.api.results;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.phananh.api.response.BaseResponse;

/**
 * Created by CAOPHUONGDUC on 4/12/2018.
 */

public class SignUpResults extends BaseResponse {
    @SerializedName("results")
    @Expose
    public SignUpResults SignUpResults;

    public com.phananh.api.results.SignUpResults getSignUpResults() {
        return SignUpResults;
    }
}
