package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.enums.KieuThanhToan;
import com.example.BusTicketBookingBackend.models.DonDatVe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DonDatVeRepository extends JpaRepository<DonDatVe, Integer> {

    List<DonDatVe> findAllByNguoiDung_Email(String mail);

    List<DonDatVe> findByTrangThaiThanhToanAndKieuThanhToanAndThoiGianDatBefore(
            int trangThaiThanhToan,
            KieuThanhToan kieuThanhToan,
            LocalDateTime thoiGianDat);

    @Query("SELECT ddv FROM DonDatVe ddv " +
            "JOIN Vexe vx ON vx.donDatVe.id = ddv.id " +
            "JOIN DatGhe dg ON dg.id = vx.datGhe.id " +
            "WHERE dg.chuyenXe.id = :chuyenXeId")
    List<DonDatVe> findByChuyenXeId(@Param("chuyenXeId") int chuyenXeId);

}
