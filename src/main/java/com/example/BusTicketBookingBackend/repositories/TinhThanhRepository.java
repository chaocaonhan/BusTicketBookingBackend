package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.TinhThanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TinhThanhRepository extends JpaRepository<TinhThanh, Integer> {
    List<TinhThanh> findAll();

    @Query("select t from TinhThanh t where t.tenTinhThanh = :tenTinh")
    TinhThanh findByTen(String tenTinh);

    void deleteByTenTinhThanh(String tenTinh);

}
