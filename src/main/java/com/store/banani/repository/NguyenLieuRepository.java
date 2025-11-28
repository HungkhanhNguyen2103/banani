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
    @Query(value = "SELECT * FROM NGUYENLIEU ",nativeQuery = true)
    List<Object[]> findAllItem();

    @Modifying
    @Transactional
    @Query(value = "insert into NGUYENLIEU values (:maNL,:tenNL,:donViTinh,:tonKho,:maCN);",
            nativeQuery = true)
    void insert(@Param("maNL") String maNL,
                @Param("tenNL") String tenNL,
                @Param("donViTinh") String donViTinh,
                @Param("tonKho") int tonKho,
                @Param("maCN") String maCN);

    @Query(value = "select * from NGUYENLIEU where MaNL = :maNL",
            nativeQuery = true)
    List<Object[]> get(@Param("maNL") String maNL);


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
}
