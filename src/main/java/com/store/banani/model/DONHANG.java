package com.store.banani.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "DONDAT")
public class DONHANG {
    @Id
    @Column(name = "MaDD")
    private String maDD;
    @Column(name = "ThoiGianDat")
    private Date thoiGianDat;
    @Column(name = "MaKH")
    private String maKH;
    @Column(name = "MaNV")
    private String maNV;
    @Column(name = "MaBAN")
    private String maBAN;
    @Column(name = "TrangThai")
    private String trangThai;

    public String getMaDD() {
        return maDD;
    }

    public void setMaDD(String maDD) {
        this.maDD = maDD;
    }

    public Date getThoiGianDat() {
        return thoiGianDat;
    }

    public void setThoiGianDat(Date thoiGianDat) {
        this.thoiGianDat = thoiGianDat;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaBAN() {
        return maBAN;
    }

    public void setMaBAN(String maBAN) {
        this.maBAN = maBAN;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
