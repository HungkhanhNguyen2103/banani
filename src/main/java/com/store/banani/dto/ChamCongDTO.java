package com.store.banani.dto;

import java.util.Date;

public class ChamCongDTO {
    private String maCC;
    private String maNV;
    private String maCa;
    private Date ngayLam;
    private String ngayLamHienThi;
    private Date gioVao;
    private String gioVaoHienThi;
    private Date gioRa;
    private String gioRaHienThi;
    private String ghiChu;

    public String getMaCC() {
        return maCC;
    }

    public void setMaCC(String maCC) {
        this.maCC = maCC;
    }

    public String getMaCa() {
        return maCa;
    }

    public void setMaCa(String maCa) {
        this.maCa = maCa;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getNgayLam() {
        return ngayLam;
    }

    public void setNgayLam(Date ngayLam) {
        this.ngayLam = ngayLam;
    }

    public String getNgayLamHienThi() {
        return ngayLamHienThi;
    }

    public void setNgayLamHienThi(String ngayLamHienThi) {
        this.ngayLamHienThi = ngayLamHienThi;
    }

    public Date getGioVao() {
        return gioVao;
    }

    public void setGioVao(Date gioVao) {
        this.gioVao = gioVao;
    }

    public String getGioVaoHienThi() {
        return gioVaoHienThi;
    }

    public void setGioVaoHienThi(String gioVaoHienThi) {
        this.gioVaoHienThi = gioVaoHienThi;
    }

    public Date getGioRa() {
        return gioRa;
    }

    public void setGioRa(Date gioRa) {
        this.gioRa = gioRa;
    }

    public String getGioRaHienThi() {
        return gioRaHienThi;
    }

    public void setGioRaHienThi(String gioRaHienThi) {
        this.gioRaHienThi = gioRaHienThi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
