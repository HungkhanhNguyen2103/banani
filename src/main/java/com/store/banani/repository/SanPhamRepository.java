package com.store.banani.repository;
import com.store.banani.model.SANPHAM;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SANPHAM, String> {
    @Query(value = "select * from SANPHAM as sp " +
                   "inner join LOAISP as lsp on sp.MaLoaiSP = lsp.MaLoaiSP;",nativeQuery = true)
    List<Object[]> findAllSanPham();

    @Query(value = "select * from LOAISP ",nativeQuery = true)
    List<Object[]> findAllLoaiSanPham();

    @Query(value = "SELECT * FROM NGUYENLIEU WHERE MaCN = :maCN ",nativeQuery = true)
    List<Object[]> findAllItemNL(@Param("maCN") String maCN);

    @Modifying
    @Transactional
    @Query(value = "insert into SANPHAM values (:maSP,:tenSP,:maLoaiSP,:donGia,:donviTinh,:trangThai,:hinhAnh);",
            nativeQuery = true)
    void insert(@Param("maSP") String maSP,
                        @Param("tenSP") String tenSP,
                        @Param("maLoaiSP") String maLoaiSP,
                        @Param("donGia") float donGia,
                        @Param("donviTinh") String donviTinh,
                        @Param("trangThai") String trangThai,
                        @Param("hinhAnh") String hinhAnh);

    @Modifying
    @Transactional
    @Query(value = "insert into CT_SANPHAM values (:maCTSP,:soLuong,:maSP,:maNL);",
            nativeQuery = true)
    void insertNL(@Param("maCTSP") String maCTSP,
                @Param("soLuong") int soLuong,
                @Param("maSP") String maSP,
                @Param("maNL") String maNL);

    @Modifying
    @Transactional
    @Query(value = "update NGUYENLIEU set TonKho = :tonKho where MaNL = :maNL",
            nativeQuery = true)
    void updateKho(@Param("tonKho") int tonKho,
                @Param("maNL") String maNL);

    @Modifying
    @Transactional
    @Query(value = "update CT_SANPHAM set SoLuong = :soLuong, MaSP = :maSP,MaNL = :maNL where MaCTSP = :maCTSP",
            nativeQuery = true)
    void updateNL(@Param("soLuong") int soLuong,
                  @Param("maSP") String maSP,
                  @Param("maNL") String maNL,
                  @Param("maCTSP") String maCTSP);

    @Query(value = "SELECT * FROM CT_SANPHAM WHERE MaCTSP = :maCTSP ",nativeQuery = true)
    List<Object[]> getCTNL(@Param("maCTSP") String maCTSP);

    @Query(value = "select ct.MaCTSP,ct.MaSP,sp.TenSP,nl.MaNL,nl.TonKho,nl.TenNL,ct.SoLuong,nl.DonViTinh" +
            " from CT_SANPHAM ct INNER JOIN NGUYENLIEU nl ON ct.MaNL = nl.MaNL " +
            " INNER JOIN SANPHAM sp ON ct.MaSP = sp.MaSP " +
            " where ct.MaSP = :maSP",
            nativeQuery = true)
    List<Object[]> getNguyenLieu(@Param("maSP") String maSP);


    @Query(value = "select * from SANPHAM where MaSP = :maSP",
            nativeQuery = true)
    List<Object[]> get(@Param("maSP") String maSP);

    @Query(value = "select * from NGUYENLIEU where MaNL = :maNL",
            nativeQuery = true)
    List<Object[]> getNguyenLieu2(@Param("maNL") String maNL);

    @Modifying
    @Transactional
    @Query(value = "update SANPHAM set TenSP = :tenSP, MaLoaiSP = :maLoaiSP,DonGia = :donGia, DonViTinh = :donviTinh, TrangThai = :trangThai, HinhAnh = :hinhAnh where MaSP = :maSP",
            nativeQuery = true)
    void update(@Param("maSP") String maSP,
                @Param("tenSP") String tenSP,
                @Param("maLoaiSP") String maLoaiSP,
                @Param("donGia") float donGia,
                @Param("donviTinh") String donviTinh,
                @Param("trangThai") String trangThai,
                @Param("hinhAnh") String hinhAnh);

    @Modifying
    @Transactional
    @Query(value = "delete SANPHAM where MaSP = :maSP",
            nativeQuery = true)
    void delete(@Param("maSP") String maSP);

    @Modifying
    @Transactional
    @Query(value = "delete CT_SANPHAM where MaCTSP = :maCTSP",
            nativeQuery = true)
    void deleteNL(@Param("maCTSP") String maCTSP);
}
