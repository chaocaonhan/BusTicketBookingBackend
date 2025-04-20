package com.example.BusTicketBookingBackend.repositories;

import com.example.BusTicketBookingBackend.models.NguoiDung;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;


@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {
    @Query("SELECT n FROM NguoiDung n JOIN FETCH n.vaiTro WHERE n.id = :id")
    Optional<NguoiDung> getNguoiDungsById( int id);

    Boolean existsNguoiDungByEmail(String email);
    Optional<NguoiDung> findByEmail(String email);
    List<NguoiDung> findAll();
    Boolean deleteNguoiDungById(Integer id);
    Page<NguoiDung> findAll(Pageable pageable);

    @Query("SELECT n FROM NguoiDung n WHERE " +
            "LOWER(n.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(n.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(n.SDT) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<NguoiDung> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
