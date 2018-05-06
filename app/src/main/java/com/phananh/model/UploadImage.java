package com.phananh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UploadImage {
    @SerializedName("created")
    @Expose
    private Date created;

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("fieldname")
    @Expose
    private String fieldname;

    @SerializedName("originalname")
    @Expose
    private String originalname;

    @SerializedName("encoding")
    @Expose
    private String encoding;

    @SerializedName("destination")
    @Expose
    private String destination;


    @SerializedName("path")
    @Expose
    private String path;


    @SerializedName("size")
    @Expose
    private Long size;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getOriginalname() {
        return originalname;
    }

    public void setOriginalname(String originalname) {
        this.originalname = originalname;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }


    public UploadImage(Date created, String id, String fieldname, String originalname, String encoding, String destination, String path, Long size) {
        this.created = created;
        this.id = id;
        this.fieldname = fieldname;
        this.originalname = originalname;
        this.encoding = encoding;
        this.destination = destination;
        this.path = path;
        this.size = size;
    }
}
