package com.phananh.api.results;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.phananh.api.response.BaseResponse;
import com.phananh.model.UserProfiles;

import java.util.List;

/**
 * Created by LeNghi on 5/4/2018.
 */

public class UserProfilesResults extends BaseResponse {
    @SerializedName("results")
    @Expose
    public List<UserProfiles> UserProfiles;

    public List<com.phananh.model.UserProfiles> getUserProfiles() {
        return UserProfiles;
    }
}
