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

    @Query(value = "select * from SANPHAM where MaSP = :maSP",
            nativeQuery = true)
    List<Object[]> get(@Param("maSP") String maSP);

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
}
