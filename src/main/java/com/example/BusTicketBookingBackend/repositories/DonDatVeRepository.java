package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.DonDatVe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonDatVeRepository extends JpaRepository<DonDatVe, Integer> {

    List<DonDatVe> findAllByNguoiDung_Email(String mail);
}
