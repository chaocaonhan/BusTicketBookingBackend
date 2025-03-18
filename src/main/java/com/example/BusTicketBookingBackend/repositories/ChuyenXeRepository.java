package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.ChuyenXe;
import com.example.BusTicketBookingBackend.models.TuyenXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChuyenXeRepository extends JpaRepository<ChuyenXe, Integer> {
    List<ChuyenXe> findChuyenXeByTuyenXe(TuyenXe tuyenXe);

    List<ChuyenXe> findAll();
}
