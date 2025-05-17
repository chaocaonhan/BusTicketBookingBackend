package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {

    DanhGia findByDonDatVe_Id(int id);
}
