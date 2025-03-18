package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.TaiXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaiXeRepository extends JpaRepository<TaiXe, Integer> {
    List<TaiXe> findAll();
    TaiXe findByHoTen(String hoTen);
}
