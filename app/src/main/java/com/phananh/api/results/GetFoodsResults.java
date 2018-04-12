package com.phananh.api.results;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.phananh.api.response.BaseResponse;
import com.phananh.model.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CAOPHUONGDUC on 4/12/2018.
 */

public class GetFoodsResults extends BaseResponse {
    @SerializedName("results")
    @Expose
    public List<Food> Food;

    public List<com.phananh.model.Food> getFood() {
        if (Food == null) {
            Food = new ArrayList<>();
        }
        return Food;
    }
}
