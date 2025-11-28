package com.store.banani.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "NGUYENLIEU")
public class NGUYENLIEU {
    @Id
    @Column(name = "MaNL")
    private String maNL;
    @Column(name = "TenNL")
    private String tenNL;
    @Column(name = "DonViTinh")
    private String donViTinh;
    @Column(name = "TonKho")
    private Date tonKho;
    @Column(name = "MaCN")
    private String maCN;

    public String getMaNL() {
        return maNL;
    }

    public void setMaNL(String maNL) {
        this.maNL = maNL;
    }

    public String getTenNL() {
        return tenNL;
    }

    public void setTenNL(String tenNL) {
        this.tenNL = tenNL;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public Date getTonKho() {
        return tonKho;
    }

    public void setTonKho(Date tonKho) {
        this.tonKho = tonKho;
    }

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }
}
