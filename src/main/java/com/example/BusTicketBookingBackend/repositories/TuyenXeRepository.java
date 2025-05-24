package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.TuyenXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TuyenXeRepository extends JpaRepository<TuyenXe, Integer> {
    List<TuyenXe> findAll();

    @Query("SELECT tx FROM TuyenXe tx WHERE tx.tinhDi.tenTinhThanh = :tinhDi AND tx.tinhDen.tenTinhThanh = :tinhDen")
    Optional<TuyenXe> findByTinhDiAndTinhDen(String tinhDi, String tinhDen);

    TuyenXe findByTenTuyen(String tenTuyen);

    Optional<TuyenXe> findTuyenXeById(int id);

    @Query("SELECT DISTINCT d1.tuyenXe FROM DiemDungTrenTuyen d1 " +
            "JOIN DiemDungTrenTuyen d2 ON d1.tuyenXe.id = d2.tuyenXe.id " +
            "WHERE d1.diemDonTra.id = :diemDiId " +
            "AND d2.diemDonTra.id = :diemDenId " +
            "AND d1.thuTuDiemDung < d2.thuTuDiemDung " +
            "AND d1.trangThai = 1 " +
            "AND d2.trangThai = 1")
    List<TuyenXe> findTuyenXeByDiemDiAndDiemDen(@Param("diemDiId") int diemDiId, @Param("diemDenId") int diemDenId);
}
