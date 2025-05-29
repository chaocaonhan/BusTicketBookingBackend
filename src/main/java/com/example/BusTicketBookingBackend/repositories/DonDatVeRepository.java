package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.enums.KieuThanhToan;
import com.example.BusTicketBookingBackend.enums.TrangThaiDonDat;
import com.example.BusTicketBookingBackend.models.DonDatVe;
import com.example.BusTicketBookingBackend.models.NguoiDung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    Optional<DonDatVe> findById(int id);

    Page<DonDatVe> findAllByTrangThaiDonDat(TrangThaiDonDat trangThaiDonDat, Pageable pageable);

    Page<DonDatVe> findAll(Pageable pageable);

    @Query("SELECT d FROM DonDatVe d WHERE (d.tenHanhKhach LIKE %:keyword% OR d.SDT LIKE %:keyword%) AND d.trangThaiDonDat = :trangThaiDonDat")
    Page<DonDatVe> findByKeywordAndTrangThai(
            @Param("keyword") String keyword,
            @Param("trangThaiDonDat") TrangThaiDonDat trangThaiDonDat,
            Pageable pageable
    );

}
