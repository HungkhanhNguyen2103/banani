package com.store.banani.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "CHINHANH")
public class CHINHANH {
    @Id
    @Column(name = "MaCN")
    private String maCN;
    @Column(name = "TenCN")
    private String tenCN;
    @Column(name = "DiaChi")
    private String diaChi;
    @Column(name = "SdtCN")
    private String sdtCN;
    @Column(name = "EmailCN")
    private String emailCN;
    @Column(name = "TrangThai")
    private String trangThai;

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }

    public String getTenCN() {
        return tenCN;
    }

    public void setTenCN(String tenCN) {
        this.tenCN = tenCN;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdtCN() {
        return sdtCN;
    }

    public void setSdtCN(String sdtCN) {
        this.sdtCN = sdtCN;
    }

    public String getEmailCN() {
        return emailCN;
    }

    public void setEmailCN(String emailCN) {
        this.emailCN = emailCN;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
