package com.phananh.model;



import android.content.Context;

import com.phananh.util.SharedPreference;

import java.io.Serializable;

/**
 * Created by Minamino on 1/10/2016.
 */
public class MonAn implements Serializable {
    private String idMonAnh;
    private String tenMonAn;
    private String moTa;
    private String url;
    private String cachLam;
    private String ngyenLieu;


    public MonAn(String idMonAnh, String tenMonAn, String moTa, String url, String cachLam, String ngyenLieu) {
        this.idMonAnh = idMonAnh;
        this.tenMonAn = tenMonAn;
        this.moTa = moTa;
        this.url = url;
        this.cachLam = cachLam;
        this.ngyenLieu = ngyenLieu;

    }


    public String getCachLam() {
        return cachLam;
    }

    public void setCachLam(String cachLam) {
        this.cachLam = cachLam;
    }


    public String getNgyenLieu() {
        return ngyenLieu;
    }

    public void setNgyenLieu(String ngyenLieu) {
        this.ngyenLieu = ngyenLieu;
    }

    public MonAn(String tenMonAn, String idMonAnh, String moTa, String url) {
        this.tenMonAn = tenMonAn;
        this.idMonAnh = idMonAnh;
        this.moTa = moTa;
        this.url = url;
    }

    public String getIdMonAnh() {
        return idMonAnh;
    }

    public void setIdMonAnh(String idMonAnh) {
        this.idMonAnh = idMonAnh;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }


    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
