package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTro, Integer> {
    VaiTro findById(int id);
}
