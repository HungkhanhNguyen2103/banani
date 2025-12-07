package com.store.banani.dto;

import java.util.ArrayList;
import java.util.Date;

public class DonDatDTO {
    private String maDD;
    private String thoiGianDat;
    private String maNV;
    private String maKH;
    private String tenKH;
    private String SDT;
    private String EmailKH;
    private String maBan;
    private String trangThai;
    private String thanhToan;
    private String nut;
    private boolean payment;
    private boolean inBill;
    private float tongTien2;

    private ArrayList<CTDonDatDTO> listSP;

    public String getMaDD() {
        return maDD;
    }

    public void setMaDD(String maDD) {
        this.maDD = maDD;
    }

    public String getThoiGianDat() {
        return thoiGianDat;
    }

    public void setThoiGianDat(String thoiGianDat) {
        this.thoiGianDat = thoiGianDat;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public ArrayList<CTDonDatDTO> getListSP() {
        return listSP;
    }

    public void setListSP(ArrayList<CTDonDatDTO> listSP) {
        this.listSP = listSP;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public String getEmailKH() {
        return EmailKH;
    }

    public void setEmailKH(String emailKH) {
        EmailKH = emailKH;
    }

    public String getNut() {
        return nut;
    }

    public void setNut(String nut) {
        this.nut = nut;
    }

    public String getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(String thanhToan) {
        this.thanhToan = thanhToan;
    }

    public float getTongTien2() {
        return tongTien2;
    }

    public void setTongTien2(float tongTien2) {
        this.tongTien2 = tongTien2;
    }

    public boolean isInBill() {
        return inBill;
    }

    public void setInBill(boolean inBill) {
        this.inBill = inBill;
    }
}
