package com.store.banani.dto;

import java.util.List;

public class LichTuanDTO {
    private String ngayLam;
    private String thu;
    private String maCa;
    private String tenCa;
    private List<String> nhanVien;

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getNgayLam() {
        return ngayLam;
    }

    public void setNgayLam(String ngayLam) {
        this.ngayLam = ngayLam;
    }

    public String getMaCa() {
        return maCa;
    }

    public void setMaCa(String maCa) {
        this.maCa = maCa;
    }

    public String getTenCa() {
        return tenCa;
    }

    public void setTenCa(String tenCa) {
        this.tenCa = tenCa;
    }

    public List<String> getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(List<String> nhanVien) {
        this.nhanVien = nhanVien;
    }
}
