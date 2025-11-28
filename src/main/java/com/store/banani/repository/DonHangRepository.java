package com.store.banani.repository;

import com.store.banani.model.CHINHANH;
import com.store.banani.model.DONHANG;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DonHangRepository extends JpaRepository<DONHANG, String> {

    @Query(value = "select * from DONDAT dd " +
            "inner join KHACHHANG kh on dd.MaKH = kh.MaKH " +
            "inner join BAN b on b.MaBAN = dd.MaBAN",nativeQuery = true)
    List<Object[]> getAllDonDat();


    @Query(value = "select * from DONDAT dd " +
            "inner join KHACHHANG kh on dd.MaKH = kh.MaKH " +
            "inner join BAN b on b.MaBAN = dd.MaBAN " +
            "left join CT_DONDAT ct on dd.MaDD = ct.MaDD " +
            "left join SANPHAM sp on ct.MaSP = sp.MaSP " +
            "where dd.MaDD = :maDD",nativeQuery = true)
    List<Object[]> chitietDonDat(@Param("maDD") String maDD);

    @Query(value = "select MaHD from HOADON where MaDD = :maDD",nativeQuery = true)
    List<Object[]> getPayment(@Param("maDD") String maDD);

    @Modifying
    @Transactional
    @Query(value = " INSERT INTO CT_DONDAT VALUES (:maDD,:maSP,:soLuong,:ghiChu)",
            nativeQuery = true)
    void insertChiTiet(@Param("maDD") String maDD,
                @Param("maSP") String maSP,
                @Param("soLuong") int soLuong,
                @Param("ghiChu") String ghiChu);

    @Modifying
    @Transactional
    @Query(value = " INSERT INTO HOADON VALUES (:maHD,:maDD,GETDATE(),:tongTien,:pttt,:maNV);" +
            " UPDATE DONDAT SET TrangThai = 'Hoàn thành' WHERE MaDD = :maDD;",
            nativeQuery = true)
    void payment(@Param("maHD") String maHD,
                       @Param("maDD") String maDD,
                       @Param("tongTien") float tongTien,
                       @Param("pttt") String pttt,
                       @Param("maNV") String maNV);

    @Query(value = "select * from KHACHHANG",nativeQuery = true)
    List<Object[]> getListKH();

    @Query(value = "select * from CT_DONDAT where MaDD = :maDD and MaSP = :maSP ",nativeQuery = true)
    List<Object[]> getDonDat(@Param("maDD") String maDD,
                             @Param("maSP") String maSP);

    @Query(value = "select * from DONDAT dd " +
                  "inner join KHACHHANG kh on dd.MaKH = kh.MaKH " +
                  "inner join BAN b on b.MaBAN = dd.MaBAN " +
                  "inner join HOADON hd on hd.MaDD = dd.MaDD " +
                  "inner join NHANVIEN nv on hd.MaNV = nv.MaNV",nativeQuery = true)
    List<Object[]> getAllHoaDon();

    @Modifying
    @Transactional
    @Query(value = " UPDATE CT_DONDAT SET SoLuong = :soLuong, GhiChu = :ghiChu WHERE MaDD = :maDD AND MaSP = :maSP",
            nativeQuery = true)
    void updateChiTiet(@Param("maDD") String maDD,
                       @Param("maSP") String maSP,
                       @Param("soLuong") int soLuong,
                       @Param("ghiChu") String ghiChu);

    @Modifying
    @Transactional
    @Query(value = " INSERT INTO DONDAT VALUES (:maDD,GETDATE(),:maKH,:maNV,:maBan,:trangThai)",
            nativeQuery = true)
    void createDondat(@Param("maDD") String maDD,
                       @Param("maKH") String maKH,
                       @Param("maNV") String maNV,
                       @Param("maBan") String maBan,
                      @Param("trangThai") String trangThai);

    @Modifying
    @Transactional
    @Query(value = " DELETE CT_DONDAT WHERE MaDD = :maDD AND MaSP = :maSP",
            nativeQuery = true)
    void removeChiTiet(@Param("maDD") String maDD,
                       @Param("maSP") String maSP);

    @Modifying
    @Transactional
    @Query(value = " UPDATE DONDAT SET TrangThai = :trangThai WHERE MaDD = :maDD",
            nativeQuery = true)
    void editDonDat(@Param("trangThai") String trangThai,
                       @Param("maDD") String maDD);



    @Query(value = "SELECT " +
            "    CAST(hd.NgayLap AS DATE) AS Ngay, " +
            "    SUM(hd.TongTien) AS TongTienTheoNgay " +
            " FROM HOADON hd " +
            " GROUP BY CAST(hd.NgayLap AS DATE) " +
            " ORDER BY Ngay;",
            nativeQuery = true)
    List<Object[]> thongKeToanchiNhanh();

    @Query(value = "SELECT " +
            "    CAST(hd.NgayLap AS DATE) AS Ngay, " +
            "    SUM(hd.TongTien) AS TongTienTheoNgay " +
            " FROM HOADON hd " +
            " WHERE hd.NgayLap >= :startDate AND hd.NgayLap <= :endDate " +
            " GROUP BY CAST(hd.NgayLap AS DATE) " +
            " ORDER BY Ngay;",
            nativeQuery = true)
    List<Object[]> thongKeToanchiNhanhByDate(@Param("startDate") String startDate,
                                       @Param("endDate") String endDate);


    @Query(value = "SELECT " +
            "    CAST(hd.NgayLap AS DATE) AS Ngay," +
            "    SUM(hd.TongTien) AS TongTienTheoNgay," +
            " cn.MaCN AS MaCN," +
            " (cn.TenCN + '-' + cn.DiaChi) AS ChiNhanh " +
            " FROM HOADON hd " +
            " INNER JOIN DONDAT dd ON hd.MaDD = dd.MaDD " +
            " INNER JOIN BAN b ON dd.MaBAN = b.MaBAN " +
            " INNER JOIN CHINHANH cn ON b.MaCN = cn.MaCN " +
            " WHERE cn.MaCN = :maCN " +
            "GROUP BY CAST(hd.NgayLap AS DATE),cn.TenCN,cn.DiaChi,cn.MaCN " +
            "ORDER BY Ngay; ",
            nativeQuery = true)
    List<Object[]> thongKechiNhanh(@Param("maCN") String maCN);

    @Query(value = "SELECT " +
            "    CAST(hd.NgayLap AS DATE) AS Ngay," +
            "    SUM(hd.TongTien) AS TongTienTheoNgay," +
            " cn.MaCN AS MaCN," +
            " (cn.TenCN + '-' + cn.DiaChi) AS ChiNhanh " +
            " FROM HOADON hd " +
            " INNER JOIN DONDAT dd ON hd.MaDD = dd.MaDD " +
            " INNER JOIN BAN b ON dd.MaBAN = b.MaBAN " +
            " INNER JOIN CHINHANH cn ON b.MaCN = cn.MaCN " +
            " WHERE cn.MaCN = :maCN AND hd.NgayLap >= :startDate AND hd.NgayLap <= :endDate " +
            "GROUP BY CAST(hd.NgayLap AS DATE),cn.TenCN,cn.DiaChi,cn.MaCN " +
            "ORDER BY Ngay; ",
            nativeQuery = true)
    List<Object[]> thongKechiNhanhbyDate(@Param("maCN") String maCN,
                                         @Param("startDate") String startDate,
                                          @Param("endDate") String endDate);


    @Query(value = " select hd.MaHD,dd.MaDD,CAST(hd.NgayLap AS DATE) AS Ngay,SUM(ctd.SoLuong * sp.DonGia) as TongTien,kh.TenKH,SUM(ctd.SoLuong) as SoLuong,cn.MaCN from HOADON hd " +
            " inner join DONDAT dd on hd.MaDD = dd.MaDD " +
            " inner join CT_DONDAT ctd on ctd.MaDD = dd.MaDD " +
            " inner join KHACHHANG kh on kh.MaKH = dd.MaKH " +
            " inner join SANPHAM sp on sp.MaSP = ctd.MaSP " +
            " inner join BAN b ON dd.MaBAN = b.MaBAN " +
            " inner join CHINHANH cn ON b.MaCN = cn.MaCN  " +
            " where cn.MaCN = :maCN " +
            " group by cn.MaCN,hd.MaHD,dd.MaDD,CAST(hd.NgayLap AS DATE),kh.TenKH " +
            " order by Ngay ",
            nativeQuery = true)
    List<Object[]> inHoaDon(@Param("maCN") String maCN);

    @Query(value = " select hd.MaHD,dd.MaDD,CAST(hd.NgayLap AS DATE) AS Ngay,SUM(ctd.SoLuong * sp.DonGia) as TongTien,kh.TenKH,SUM(ctd.SoLuong) as SoLuong,cn.MaCN from HOADON hd " +
            " inner join DONDAT dd on hd.MaDD = dd.MaDD " +
            " inner join CT_DONDAT ctd on ctd.MaDD = dd.MaDD " +
            " inner join KHACHHANG kh on kh.MaKH = dd.MaKH " +
            " inner join SANPHAM sp on sp.MaSP = ctd.MaSP " +
            " inner join BAN b ON dd.MaBAN = b.MaBAN " +
            " inner join CHINHANH cn ON b.MaCN = cn.MaCN  " +
            " where cn.MaCN = :maCN and hd.NgayLap >= :startDate and hd.NgayLap <= :endDate " +
            " group by cn.MaCN,hd.MaHD,dd.MaDD,CAST(hd.NgayLap AS DATE),kh.TenKH " +
            " order by Ngay ",
            nativeQuery = true)
    List<Object[]> inHoaDonByDate(@Param("maCN") String maCN,@Param("startDate") String startDate,@Param("endDate") String endDate);


    @Query(value = " select hd.MaHD,dd.MaDD,CAST(hd.NgayLap AS DATE) AS Ngay,SUM(ctd.SoLuong * sp.DonGia) as TongTien,kh.TenKH,SUM(ctd.SoLuong) as SoLuong,cn.MaCN from HOADON hd " +
            " inner join DONDAT dd on hd.MaDD = dd.MaDD " +
            " inner join CT_DONDAT ctd on ctd.MaDD = dd.MaDD " +
            " inner join KHACHHANG kh on kh.MaKH = dd.MaKH " +
            " inner join SANPHAM sp on sp.MaSP = ctd.MaSP " +
            " inner join BAN b ON dd.MaBAN = b.MaBAN " +
            " inner join CHINHANH cn ON b.MaCN = cn.MaCN  " +
            " where hd.NgayLap >= :startDate and hd.NgayLap <= :endDate " +
            " group by cn.MaCN,hd.MaHD,dd.MaDD,CAST(hd.NgayLap AS DATE),kh.TenKH " +
            " order by Ngay ",
            nativeQuery = true)
    List<Object[]> inHoaDonAllByDate(@Param("startDate") String startDate,@Param("endDate") String endDate);
}
