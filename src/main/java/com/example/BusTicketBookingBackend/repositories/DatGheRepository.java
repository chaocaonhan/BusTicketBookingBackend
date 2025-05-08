package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.DatGhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatGheRepository extends JpaRepository<DatGhe, Integer> {
    List<DatGhe> findByChuyenXe_Id(Integer id);
    List<DatGhe> findAll();
}
