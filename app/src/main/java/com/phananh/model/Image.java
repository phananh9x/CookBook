package com.phananh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by My on 4/11/2018.
 */

public class Image implements Serializable{
    @SerializedName("image")
    @Expose
    public String image;
}
