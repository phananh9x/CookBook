package com.phananh.api.results;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.phananh.api.response.BaseResponse;
import com.phananh.model.Food;

/**
 * Created by thieu on 4/13/2018.
 */

public class GetFoodDetailResults extends BaseResponse {
    @SerializedName("results")
    @Expose
    public Food Food;

    public com.phananh.model.Food getFoodDetail() {
        return Food;
    }
}
