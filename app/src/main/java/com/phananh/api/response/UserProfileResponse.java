package com.phananh.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.phananh.model.Comment;
import com.phananh.model.UserProfiles;

/**
 * Created by LeNghi on 5/5/2018.
 */

public class UserProfileResponse extends  BaseResponse {
    @SerializedName("results")
    @Expose
    public UserProfiles userProfiles;

    public UserProfiles getUserProfiles() {
        return userProfiles;
    }
}
