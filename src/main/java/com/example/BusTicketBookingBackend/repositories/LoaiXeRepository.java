package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.LoaiXe;
import com.example.BusTicketBookingBackend.models.TinhThanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaiXeRepository extends JpaRepository<LoaiXe, Long> {


    List<LoaiXe> findAll();

    LoaiXe findLoaiXeByTenLoaiXe(String loaiXe);
}
