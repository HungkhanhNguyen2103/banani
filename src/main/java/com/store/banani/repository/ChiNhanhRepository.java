package com.store.banani.repository;

import com.store.banani.model.CHINHANH;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiNhanhRepository extends JpaRepository<CHINHANH, String> {
    @Query(value = "SELECT * FROM CHINHANH ",nativeQuery = true)
    List<Object[]> findAllChiNhanh();

    @Modifying
    @Transactional
    @Query(value = "insert into CHINHANH values (:maCN,:tenCN,:diaChi,:sdtCN,:emailCN,:trangThai);",
            nativeQuery = true)
    void insert(@Param("maCN") String maCN,
                @Param("tenCN") String tenCN,
                @Param("diaChi") String diaChi,
                @Param("sdtCN") String sdtCN,
                @Param("emailCN") String emailCN,
                @Param("trangThai") String trangThai);

    @Query(value = "select * from CHINHANH where MaCN = :maCN",
            nativeQuery = true)
    List<Object[]> get(@Param("maCN") String maCN);


    @Modifying
    @Transactional
    @Query(value = "update CHINHANH set TenCN = :tenCN, DiaChi = :diaChi,SdtCN = :sdtCN, EmailCN = :emailCN, TrangThai = :trangThai where MaCN = :maCN",
            nativeQuery = true)
    void update(@Param("tenCN") String tenCN,
                @Param("diaChi") String diaChi,
                @Param("sdtCN") String sdtCN,
                @Param("emailCN") String emailCN,
                @Param("maCN") String maCN,
                @Param("trangThai") String trangThai);

    @Modifying
    @Transactional
    @Query(value = "delete CHINHANH where MaCN = :maCN",
            nativeQuery = true)
    void delete(@Param("maCN") String maCN);
}
