package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.Xe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XeRepository extends JpaRepository<Xe, Integer> {
    List<Xe> findAll();

    Xe findByBienSo(String bienSo);
}
