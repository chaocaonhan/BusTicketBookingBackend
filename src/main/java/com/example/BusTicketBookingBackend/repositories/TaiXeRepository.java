package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.TaiXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaiXeRepository extends JpaRepository<TaiXe, Integer> {
    List<TaiXe> findAll();
    TaiXe findByHoTen(String hoTen);

    @Query("SELECT tx FROM TaiXe tx WHERE tx.id NOT IN (" +
            "SELECT cx.taiXe.id FROM ChuyenXe cx WHERE cx.ngayKhoiHanh = :ngayKhoiHanh " +
            "GROUP BY cx.taiXe.id HAVING COUNT(cx) >= 2) " +
            "AND tx.id NOT IN (" +
            "SELECT cx.taiXe.id FROM ChuyenXe cx WHERE cx.ngayKhoiHanh = :ngayKhoiHanh " +
            "AND cx.gioKetThuc >= :gioKhoiHanh)")
    List<TaiXe> findAvailableDrivers(@Param("ngayKhoiHanh") LocalDate ngayKhoiHanh,
                                     @Param("gioKhoiHanh") LocalTime gioKhoiHanh);

    Optional<TaiXe> findById(Integer id);
}
