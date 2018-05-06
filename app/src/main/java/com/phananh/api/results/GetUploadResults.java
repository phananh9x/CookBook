package com.phananh.api.results;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.phananh.api.response.BaseResponse;
import com.phananh.model.UploadImage;

public class GetUploadResults extends BaseResponse {
    @SerializedName("results")
    @Expose
    public UploadImage UploadImage;

    public com.phananh.model.UploadImage getUploadImage() {
        return UploadImage;
    }
}
