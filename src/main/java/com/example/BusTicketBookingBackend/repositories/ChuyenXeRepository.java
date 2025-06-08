package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.dtos.response.ChuyenXeResponse;
import com.example.BusTicketBookingBackend.models.ChuyenXe;
import com.example.BusTicketBookingBackend.models.TuyenXe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChuyenXeRepository extends JpaRepository<ChuyenXe, Integer> {
    List<ChuyenXe> findChuyenXeByTuyenXe(TuyenXe tuyenXe);

    List<ChuyenXe> findAll();

    @Query("SELECT c.tuyenXe, COUNT(c.id) AS soChuyen " +
            "FROM ChuyenXe c " +
            "GROUP BY c.tuyenXe " +
            "ORDER BY soChuyen DESC")
    List<Object[]> findTop5TuyenXePhoBien();

    List<ChuyenXe> findByNgayKhoiHanhGreaterThanEqual(LocalDate today);

    Page<ChuyenXe> findAllByTrangThai(ChuyenXe.TrangThai trangThaiChuyenXe, Pageable pageable);

    @Query("SELECT cx FROM ChuyenXe cx WHERE cx.ngayKhoiHanh = :keyword AND cx.trangThai = :trangThaiChuyenXe")
    Page<ChuyenXe> findByNgayKhoiHanhAndTrangThai(
            @Param("keyword") LocalDate keyword,
            @Param("trangThaiChuyenXe") ChuyenXe.TrangThai trangThaiChuyenXe,
            Pageable pageable
    );

    List<ChuyenXe> findByTaiXeIdAndNgayKhoiHanhBetween(int idTaiXe, LocalDate from, LocalDate to);

}
