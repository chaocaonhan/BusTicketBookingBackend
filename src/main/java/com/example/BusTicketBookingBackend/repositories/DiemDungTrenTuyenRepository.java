package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.DiemDungTrenTuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiemDungTrenTuyenRepository extends JpaRepository<DiemDungTrenTuyen, Integer> {
    List<DiemDungTrenTuyen> findAll();

    @Query("SELECT d FROM DiemDungTrenTuyen d WHERE d.tuyenXe.id = :tuyenId and d.trangThai = 1")
    List<DiemDungTrenTuyen> findByTuyenId(@Param("tuyenId") int tuyenId);



    Optional<DiemDungTrenTuyen> findFirstByTuyenXeIdAndTrangThaiOrderByThuTuDiemDungAsc(int tuyenXeId, int trangThai);


    Optional<DiemDungTrenTuyen> findFirstByTuyenXeIdAndTrangThaiOrderByThuTuDiemDungDesc(int tuyenXeId, int trangThai);
}
