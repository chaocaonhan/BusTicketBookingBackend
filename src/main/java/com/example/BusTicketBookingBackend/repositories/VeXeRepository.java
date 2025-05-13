package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.Vexe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeXeRepository extends JpaRepository<Vexe, Integer> {
    List<Vexe> findAllByDonDatVe_Id(Integer maDonDat);
}
