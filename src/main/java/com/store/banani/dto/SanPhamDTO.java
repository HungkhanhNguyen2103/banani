package com.store.banani.dto;

public class SanPhamDTO {
    private String maSP;
    private String tenSP;
    private String maLSP;
    private float donGia;
    private String donviTinh;
    private String trangThai;
    private String tenLoaiSP;
    private String hinhAnh;
    private String donGiaText;

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMaLSP() {
        return maLSP;
    }

    public void setMaLSP(String maLSP) {
        this.maLSP = maLSP;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public String getDonviTinh() {
        return donviTinh;
    }

    public void setDonviTinh(String donviTinh) {
        this.donviTinh = donviTinh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        if(hinhAnh == null) this.hinhAnh = "";
        else this.hinhAnh = hinhAnh;
    }

    public String getDonGiaText() {
        return donGiaText;
    }

    public void setDonGiaText(String donGiaText) {
        this.donGiaText = donGiaText;
    }
}
