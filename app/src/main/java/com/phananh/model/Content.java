package com.phananh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by My on 4/11/2018.
 */

public class Content {
    @SerializedName("step")
    @Expose
    private String step;

    @SerializedName("arrImage")
    @Expose
    private List<Image> arrImage;


    public String getStep() {
        return step;
    }

    public List<Image> getArrImage() {
        if (arrImage == null){
            arrImage = new ArrayList<>();
        }
        return arrImage;
    }
}
