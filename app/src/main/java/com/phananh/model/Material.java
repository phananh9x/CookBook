package com.phananh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by My on 4/11/2018.
 */

public class Material implements Serializable{
    @SerializedName("material")
    @Expose
    public String material;

    @SerializedName("amount")
    @Expose
    public String amount;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
