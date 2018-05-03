package com.phananh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
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
    public List<String> favourite;
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

    public List<Material> getMaterials() {
        if (materials == null) {
            materials = new ArrayList<>();
        }
        return materials;
    }

    public void setMaterials(List<Material> materials) {

        this.materials = materials;
    }

    public List<Content> getContent() {
        if (content == null) {
            content = new ArrayList<>();
        }
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public List<String> getFavourite() {
        if(favourite== null) {
            favourite = new ArrayList<>();
        }
        return favourite;
    }

    public void setFavourite(List<String> favourite) {
        this.favourite = favourite;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDecriptions() {
        return decriptions;
    }

    public void setDecriptions(String decriptions) {
        this.decriptions = decriptions;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

}
