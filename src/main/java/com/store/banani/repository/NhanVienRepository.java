package com.store.banani.repository;

import com.store.banani.model.NHANVIEN;
import com.store.banani.model.TAIKHOAN;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Repository
public interface NhanVienRepository extends JpaRepository<NHANVIEN, String> {

    @Modifying
    @Transactional
    @Query(value = " INSERT INTO NHANVIEN VALUES (:maNV, :tenNV, :gioiTinh, :ngaySinh, :sdtNV, :emailNV, :vaiTro, :maCN); ",
            nativeQuery = true)
    void insertNhanVien(@Param("maNV") String MaNV,
                        @Param("tenNV") String tenNV,
                        @Param("gioiTinh") String gioiTinh,
                        @Param("ngaySinh") Date ngaySinh,
                        @Param("sdtNV") String sdtNV,
                        @Param("emailNV") String emailNV,
                        @Param("vaiTro") String vaiTro,
                        @Param("maCN") String maCN);

    @Query(value = "select * from NHANVIEN nv " +
            "left join TAIKHOAN tk on nv.MaNV = tk.MaNV where nv.MaNV = :maNV",
            nativeQuery = true)
    List<Object[]> getNhanVien(@Param("maNV") String MaNV);


    @Modifying
    @Transactional
    @Query(value = " UPDATE NHANVIEN SET TenNV = :tenNV, GioiTinh = :gioiTinh,NgaySinh = :ngaySinh,SdtNV = :sdtNV,EmailNV = :emailNV,VaiTro = :vaiTro,MaCN = :maCN WHERE MaNV = :maNV; ",
            nativeQuery = true)
    void update(@Param("maNV") String MaNV,
                        @Param("tenNV") String tenNV,
                        @Param("gioiTinh") String gioiTinh,
                        @Param("ngaySinh") Date ngaySinh,
                        @Param("sdtNV") String sdtNV,
                        @Param("emailNV") String emailNV,
                        @Param("vaiTro") String vaiTro,
                        @Param("maCN") String maCN);

    @Query(value = "select * from NHANVIEN ",
            nativeQuery = true)
    List<Object[]> getAllNhanVien();

}