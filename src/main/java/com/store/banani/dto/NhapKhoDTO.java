package com.store.banani.dto;

public class NhapKhoDTO {
    private String maPN;
    private String ngayNhap;
    private String tenNV;
    private int soLuong;
    private long donGia;
    private String donGiaShow;

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public long getDonGia() {
        return donGia;
    }

    public void setDonGia(long donGia) {
        this.donGia = donGia;
    }

    public String getDonGiaShow() {
        return donGiaShow;
    }

    public void setDonGiaShow(String donGiaShow) {
        this.donGiaShow = donGiaShow;
    }
}
