package com.store.banani.model;
import jakarta.persistence.*;
import java.util.UUID;
@Entity
@Table(name = "TAIKHOAN")
public class TAIKHOAN {
    @Id
    @Column(name = "MaTaiKhoan")
    private String maTaiKhoan;
    @Column(name = "MatKhau")
    private String matKhau;
    @Column(name = "MaNV")
    private String maNV;


    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
}
