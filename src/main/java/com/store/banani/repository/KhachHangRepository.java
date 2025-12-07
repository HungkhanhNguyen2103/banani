package com.store.banani.repository;

import com.store.banani.model.BAN;
import com.store.banani.model.KHACHHANG;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KhachHangRepository extends JpaRepository<KHACHHANG, String> {

    @Modifying
    @Transactional
    @Query(value = "insert into KHACHHANG values (:maKH,:tenKH,:sdtKH,:emailKH,GETDATE());",
            nativeQuery = true)
    void insert(@Param("maKH") String maKH,
                @Param("tenKH") String tenKH,
                @Param("sdtKH") String sdtKH,
                @Param("emailKH") String emailKH);

    @Modifying
    @Transactional
    @Query(value = "update KHACHHANG SET TenKH = :tenKH, SdtKH = :sdtKH, EmailKH = :emailKH where MaKH = :maKH ;",
            nativeQuery = true)
    void update(@Param("maKH") String maKH,
                @Param("tenKH") String tenKH,
                @Param("sdtKH") String sdtKH,
                @Param("emailKH") String emailKH);

    @Query(value = "SELECT * from KHACHHANG where SdtKH = :sdt",
            nativeQuery = true)
    List<Object[]> getKhachHang(@Param("sdt") String sdt);
}
