package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.TuyenXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TuyenXeRepository extends JpaRepository<TuyenXe, Integer> {
    List<TuyenXe> findAll();

    @Query("SELECT tx FROM TuyenXe tx WHERE tx.tinhDi.tenTinhThanh = :tinhDi AND tx.tinhDen.tenTinhThanh = :tinhDen")
    Optional<TuyenXe> findByTinhDiAndTinhDen(String tinhDi, String tinhDen);

    TuyenXe findByTenTuyen(String tenTuyen);

}
