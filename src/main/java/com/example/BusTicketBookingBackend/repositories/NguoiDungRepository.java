package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {
    @Query("SELECT n FROM NguoiDung n JOIN FETCH n.vaiTro WHERE n.id = :id")
    Optional<NguoiDung> getNguoiDungsById( int id);
    List<NguoiDung> getAllNguoiDungs();
}
