package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.DiemDonTra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiemDonTraRepository extends JpaRepository<DiemDonTra, Integer> {
    DiemDonTra findDiemDonTraById(int id);
    DiemDonTra findDiemDonTraByTenDiemDon(String tenDiemDon);
}
