package com.store.banani.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BAN")
public class BAN {
    @Id
    @Column(name = "MaBAN")
    private String maBAN;
    @Column(name = "TenBAN")
    private String tenBAN;
    @Column(name = "KhuVuc")
    private String khuVuc;
    @Column(name = "TrangThai")
    private String trangThai;

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
}
