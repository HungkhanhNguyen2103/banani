package com.store.banani.dto;

public class CTDonDatDTO {
    private String maDD;
    private String maSP;
    private SanPhamDTO sanPhamDTO;
    private int soLuong;
    private String ghiChu;

    public String getMaDD() {
        return maDD;
    }

    public void setMaDD(String maDD) {
        this.maDD = maDD;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public SanPhamDTO getSanPhamDTO() {
        return sanPhamDTO;
    }

    public void setSanPhamDTO(SanPhamDTO sanPhamDTO) {
        this.sanPhamDTO = sanPhamDTO;
    }
}
