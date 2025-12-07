package com.store.banani.repository;

import com.store.banani.model.TAIKHOAN;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TAIKHOAN, String> {
    @Query(value = "SELECT t.MaTaiKhoan, t.MaNV,nv.TenNV,nv.VaiTro,cn.DiaChi,cn.MaCN FROM TAIKHOAN as t inner join " +
            "NHANVIEN as nv on t.MaNV = nv.MaNV " +
            " inner join CHINHANH as cn on nv.MaCN = cn.MaCN " +
            " WHERE MaTaiKhoan = :maTaiKhoan AND MatKhau = :matKhau",nativeQuery = true)
    Object[] findByCredentials(@Param("maTaiKhoan") String maTaiKhoan, @Param("matKhau") String matKhau);

    @Query(value = "select * from NHANVIEN as nv " +
            "left join TAIKHOAN as tk on nv.MaNV = tk.MaNV " ,nativeQuery = true)
    List<Object[]> findAllUser();

    @Modifying
    @Transactional
    @Query(value = " INSERT INTO TAIKHOAN VALUES (:maTK, :matKhau, :maNV); ",
            nativeQuery = true)
    void insertUser(@Param("maTK") String maTK,
                        @Param("matKhau") String matKhau,
                        @Param("maNV") String maNV);

    @Modifying
    @Transactional
    @Query(value = " UPDATE TAIKHOAN SET MatKhau = :matKhau WHERE MaTaiKhoan = :maTaiKhoan; ",
            nativeQuery = true)
    void updateUser(@Param("maTaiKhoan") String maTaiKhoan,
                    @Param("matKhau") String matKhau);

    @Query(value = " SELECT MaTaiKhoan FROM TAIKHOAN WHERE MaTaiKhoan = :maTaiKhoan AND MaNV != :maNV ",
            nativeQuery = true)
    List<Object[]> getUser(@Param("maTaiKhoan") String maTaiKhoan,
                           @Param("maNV") String maNV);

    @Query(value = " SELECT MaTaiKhoan FROM TAIKHOAN WHERE MaTaiKhoan = :maTaiKhoan AND MaNV = :maNV ",
            nativeQuery = true)
    List<Object[]> getUserByMaNV(@Param("maTaiKhoan") String maTaiKhoan,
                           @Param("maNV") String maNV);
}