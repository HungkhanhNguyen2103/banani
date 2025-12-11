package com.store.banani.dto;

import java.util.ArrayList;
import java.util.List;

public class LichNgayDTO {
    private String ngayLam;
    private String thu;

    private List<String> caSang = new ArrayList<>();
    private List<String> caChieu = new ArrayList<>();
    private List<String> caToi = new ArrayList<>();

    // getter/setter

    public String getNgayLam() {
        return ngayLam;
    }

    public void setNgayLam(String ngayLam) {
        this.ngayLam = ngayLam;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public List<String> getCaSang() {
        return caSang;
    }

    public void setCaSang(List<String> caSang) {
        this.caSang = caSang;
    }

    public List<String> getCaChieu() {
        return caChieu;
    }

    public void setCaChieu(List<String> caChieu) {
        this.caChieu = caChieu;
    }

    public List<String> getCaToi() {
        return caToi;
    }

    public void setCaToi(List<String> caToi) {
        this.caToi = caToi;
    }
}