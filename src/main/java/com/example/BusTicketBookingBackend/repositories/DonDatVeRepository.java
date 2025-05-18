package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.enums.KieuThanhToan;
import com.example.BusTicketBookingBackend.models.DonDatVe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DonDatVeRepository extends JpaRepository<DonDatVe, Integer> {

    List<DonDatVe> findAllByNguoiDung_Email(String mail);

    List<DonDatVe> findByTrangThaiThanhToanAndKieuThanhToanAndThoiGianDatBefore(
            int trangThaiThanhToan,
            KieuThanhToan kieuThanhToan,
            LocalDateTime thoiGianDat);
}
