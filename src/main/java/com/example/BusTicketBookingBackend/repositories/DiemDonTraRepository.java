package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.DiemDonTra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiemDonTraRepository extends JpaRepository<DiemDonTra, Integer> {
    DiemDonTra findDiemDonTraById(int id);
    DiemDonTra findDiemDonTraByTenDiemDon(String tenDiemDon);

    List<DiemDonTra> findByTinhThanh_TenTinhThanh(String tenTinh);
    List<DiemDonTra> findByTinhThanh_TenTinhThanhAndTrangThai(String tinhThanh, Integer trangthai);

}
