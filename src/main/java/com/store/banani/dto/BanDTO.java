package com.store.banani.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class BanDTO {
    private String maBAN;
    private String tenBAN;
    private String khuVuc;
    private String trangThai;
    private String maCN;
    private String chinhanh;

    public String getMaBAN() {
        return maBAN;
    }

    public void setMaBAN(String maBAN) {
        this.maBAN = maBAN;
    }

    public String getTenBAN() {
        return tenBAN;
    }

    public void setTenBAN(String tenBAN) {
        this.tenBAN = tenBAN;
    }

    public String getKhuVuc() {
        return khuVuc;
    }

    public void setKhuVuc(String khuVuc) {
        this.khuVuc = khuVuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }

    public String getChinhanh() {
        return chinhanh;
    }

    public void setChinhanh(String chinhanh) {
        this.chinhanh = chinhanh;
    }
}
