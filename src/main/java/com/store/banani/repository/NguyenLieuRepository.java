package com.store.banani.repository;

import com.store.banani.model.CHINHANH;
import com.store.banani.model.NGUYENLIEU;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NguyenLieuRepository extends JpaRepository<NGUYENLIEU, String> {


    @Query(value = "SELECT * FROM NGUYENLIEU WHERE :maCN = 'Admin' OR  MaCN = :maCN ",nativeQuery = true)
    List<Object[]> findAllItem(@Param("maCN") String maCN);

    @Query(value = "SELECT pn.MaPN, CONVERT(varchar(10), pn.NgayNhap, 105) AS NgayNhapFormatted, pn.MaNV,nv.TenNV, SUM(ct.SoLuong) as TongSoLuong, CAST(SUM(ct.SoLuong * ct.DonGia) AS FLOAT) AS TongGia FROM PHIEUNHAPKHO pn " +
            "INNER JOIN CT_PHIEUNHAP ct ON pn.MaPN = ct.MaPN " +
            "INNER JOIN NHANVIEN nv ON pn.MaNV = nv.MaNV " +
            "WHERE pn.MaCN = :maCN OR :maCN = 'Admin' " +
            "GROUP BY pn.MaPN,pn.NgayNhap, pn.MaNV,nv.TenNV",nativeQuery = true)
    List<Object[]> findAllItemNhapKho(@Param("maCN") String maCN);

    @Modifying
    @Transactional
    @Query(value = "insert into NGUYENLIEU values (:maNL,:tenNL,:donViTinh,:tonKho,:maCN);",
            nativeQuery = true)
    void insert(@Param("maNL") String maNL,
                @Param("tenNL") String tenNL,
                @Param("donViTinh") String donViTinh,
                @Param("tonKho") int tonKho,
                @Param("maCN") String maCN);


    @Modifying
    @Transactional
    @Query(value = "insert into PHIEUNHAPKHO values (:maPN,GETDATE(),:maNV,:maCN);",
            nativeQuery = true)
    void nhapKho(@Param("maPN") String maPN,
                @Param("maNV") String maNV,
                @Param("maCN") String maCN);

    @Modifying
    @Transactional
    @Query(value = "insert into CT_PHIEUNHAP values (:maPN,:maNL,:soLuong,:donGia);",
            nativeQuery = true)
    void nhapKhoCT(@Param("maPN") String maPN,
                 @Param("maNL") String maNL,
                 @Param("soLuong") int soLuong,
                   @Param("donGia") double donGia);

    @Query(value = "select * from NGUYENLIEU where MaNL = :maNL",
            nativeQuery = true)
    List<Object[]> get(@Param("maNL") String maNL);

    @Modifying
    @Transactional
    @Query(value = "update NGUYENLIEU set TonKho = :tonKho where MaNL = :maNL",
            nativeQuery = true)
    void updateKho2(@Param("tonKho") int tonKho,
                   @Param("maNL") String maNL);


    @Modifying
    @Transactional
    @Query(value = "update NGUYENLIEU set TenNL = :tenNL, DonViTinh = :donViTinh,TonKho = :tonKho, MaCN = :maCN where MaNL = :maNL",
            nativeQuery = true)
    void update(@Param("tenNL") String tenNL,
                @Param("donViTinh") String donViTinh,
                @Param("tonKho") int tonKho,
                @Param("maCN") String maCN,
                @Param("maNL") String maNL);

    @Modifying
    @Transactional
    @Query(value = "delete NGUYENLIEU where MaNL = :maNL",
            nativeQuery = true)
    void delete(@Param("maNL") String maNL);


    @Modifying
    @Transactional
    @Query(value = "insert into PHIEUXUATKHO values (:maPX,GETDATE(),:maNV,:maCN);",
            nativeQuery = true)
    void xuatKho(@Param("maPX") String maPX,
                 @Param("maNV") String maNV,
                 @Param("maCN") String maCN);

    @Modifying
    @Transactional
    @Query(value = "insert into CT_PHIEUXUAT values (:maPX,:maNL,:soLuong);",
            nativeQuery = true)
    void xuatKhoCT(@Param("maPX") String maPX,
                 @Param("maNL") String maNL,
                 @Param("soLuong") int soLuong);
}
