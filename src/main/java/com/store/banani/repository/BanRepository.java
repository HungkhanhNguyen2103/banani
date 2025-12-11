package com.store.banani.repository;

import com.store.banani.model.BAN;
import com.store.banani.model.CHINHANH;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BanRepository extends JpaRepository<BAN, String> {
    @Query(value = "select * from BAN b inner join CHINHANH c on b.MaCN = c.MaCN WHERE :maCN = 'Admin' OR c.MaCN = :maCN;",nativeQuery = true)
    List<Object[]> findAllItem(@Param("maCN") String maCN);

    @Query(value = "select * from BAN b inner join CHINHANH c on b.MaCN = c.MaCN where b.MaBAN = :maBan",nativeQuery = true)
    List<Object[]> findItem(
            @Param("maBan") String maBan
    );

    @Query(value = "select * from BAN b where ( :maCN = 'Admin' OR b.MaCN = :maCN ) and b.TenBAN = :tenBan and b.KhuVuc = :khuVuc",nativeQuery = true)
    List<Object[]> findItem2(
            @Param("tenBan") String tenBan,
            @Param("khuVuc") String khuVuc,
            @Param("maCN") String maCN
    );

    @Modifying
    @Transactional
    @Query(value = "update BAN set TrangThai = :trangThai where MaBan = :maBan",nativeQuery = true)
    void update(
            @Param("trangThai") String trangThai,
            @Param("maBan") String maBan
    );


    @Modifying
    @Transactional
    @Query(value = "insert into BAN values (:maBan,:tenBan,:khuVuc,:TrangThai,:maCN)",nativeQuery = true)
    void insert(
            @Param("maBan") String maBan,
            @Param("tenBan") String tenBan,
            @Param("khuVuc") String khuVuc,
            @Param("TrangThai") String TrangThai,
            @Param("maCN") String maCN
    );
}
