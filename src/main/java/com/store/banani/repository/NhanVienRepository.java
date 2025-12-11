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

    @Modifying
    @Transactional
    @Query(value = " INSERT INTO CHAMCONG VALUES (:maCC, :maNV, :ngayLam, :maCa, NULL, NULL, NULL); ",
            nativeQuery = true)
    void insertLich(@Param("maCC") String maCC,
                        @Param("maNV") String maNV,
                        @Param("ngayLam") String ngayLam,
                        @Param("maCa") String maCa);

    @Query(value = "select * from CHAMCONG cc " +
            "where cc.MaNV = :maNV and cc.MaCa = :maCa and cc.NgayLam = :ngayLam ",
            nativeQuery = true)
    List<Object[]> getLich(@Param("maNV") String maNV,
                           @Param("maCa") String maCa,
                           @Param("ngayLam") String ngayLam);


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

    @Query(value = "select * from NHANVIEN WHERE MaCN = :maCN OR :maCN = 'Admin' ",
            nativeQuery = true)
    List<Object[]> getAllNhanVien(@Param("maCN") String maCN);


    @Query(value = "DECLARE @InputDate DATE = :ngayLam; " +
            "DECLARE @Monday DATE = DATEADD(WEEK, DATEDIFF(WEEK, 0, @InputDate), 0); " +
            "DECLARE @Sunday DATE = DATEADD(DAY, 6, @Monday); " +
            " " +
            "SELECT " +
            "    cc.NgayLam, " +
            "    FORMAT(cc.NgayLam, 'dddd', 'vi-VN') AS Thu, " +
            "    cc.MaCa, " +
            "    ca.TenCa, " +
            "    FORMAT(ca.GioBatDau, 'HH:mm:ss'), " +
            "    FORMAT(ca.GioKetThuc, 'HH:mm:ss'), " +
            "    cc.MaNV, " +
            "    nv.TenNV, " +
            "    FORMAT(cc.GioVao, 'HH:mm:ss'), " +
            "    FORMAT(cc.GioRa, 'HH:mm:ss'), " +
            "    cc.GhiChu, " +
            "    FORMAT(cc.NgayLam, 'dd-MM-yyyy', 'vi-VN') as NgayLamHienThi " +
            "FROM ChamCong cc " +
            "INNER JOIN CA_LAMVIEC ca ON cc.MaCa = ca.MaCa " +
            "INNER JOIN NHANVIEN nv ON cc.MaNV = nv.MaNV " +
            "WHERE cc.NgayLam BETWEEN @Monday AND @Sunday " +
            "ORDER BY cc.NgayLam, ca.MaCa,nv.TenNV;",
            nativeQuery = true)
    List<Object[]> lichLamViec(@Param("ngayLam") String ngayLam);
}