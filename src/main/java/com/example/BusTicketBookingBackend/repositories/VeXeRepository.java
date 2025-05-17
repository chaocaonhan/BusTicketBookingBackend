package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.Vexe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VeXeRepository extends JpaRepository<Vexe, Integer> {
    List<Vexe> findAllByDonDatVe_Id(Integer maDonDat);

    @Query("SELECT COUNT(v) FROM Vexe v WHERE v.donDatVe.id = :maDonDat AND v.trangThaiVe = 'CANCELED'")
    Integer countCancelledTicketsByDonDatVeId(Integer maDonDat);

    List<Vexe> findByDonDatVe_Id(int donDatVeId);
}
