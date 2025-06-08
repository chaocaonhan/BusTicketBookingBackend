package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.KhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, Integer> {
    @Query("SELECT km from KhuyenMai km where km.trangThai != 2")
    List<KhuyenMai> findAll();

}
