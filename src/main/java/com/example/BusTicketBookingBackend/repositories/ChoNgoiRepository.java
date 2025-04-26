package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.ChoiNgoi;
import com.example.BusTicketBookingBackend.models.LoaiXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoNgoiRepository extends JpaRepository<ChoiNgoi, Integer> {
    List<ChoiNgoi> findAll();

    List<ChoiNgoi> findByLoaiXe_Id(int loaiXeId);
    ChoiNgoi findById(int id);

}
