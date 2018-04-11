package com.phananh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by My on 4/11/2018.
 */

public class Food implements Serializable{
    @SerializedName("materials")
    @Expose
    public List<Material> materials = null;
    @SerializedName("content")
    @Expose
    public List<Content> content = null;
    @SerializedName("favourite")
    @Expose
    public Integer favourite;
    @SerializedName("created")
    @Expose
    public String created;
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("categoryId")
    @Expose
    public String categoryId;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("decriptions")
    @Expose
    public String decriptions;
    @SerializedName("youtube")
    @Expose
    public String youtube;
    @SerializedName("material")
    @Expose
    public String material;
}
