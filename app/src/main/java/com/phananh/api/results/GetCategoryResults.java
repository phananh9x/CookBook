package com.phananh.api.results;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.phananh.api.response.BaseResponse;
import com.phananh.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CAOPHUONGDUC on 4/12/2018.
 */

public class GetCategoryResults extends BaseResponse {
    @SerializedName("results")
    @Expose
    public List<Category> Category;

    public List<com.phananh.model.Category> getCategory() {
        if (Category == null){
            Category = new ArrayList<>();
        }
        return Category;
    }
}
