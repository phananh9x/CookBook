package com.phananh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by My on 4/11/2018.
 */

public class Material {
    @SerializedName("material")
    @Expose
    public String material;

    @SerializedName("amount")
    @Expose
    public String amount;
}
