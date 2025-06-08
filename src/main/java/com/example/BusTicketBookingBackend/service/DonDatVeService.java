package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.DatVeRequest;
import com.example.BusTicketBookingBackend.dtos.request.FindingRequest;
import com.example.BusTicketBookingBackend.dtos.response.DonDatVeResponse;
import com.example.BusTicketBookingBackend.enums.TrangThaiDonDat;
import com.example.BusTicketBookingBackend.models.DonDatVe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface DonDatVeService {
    String taoDonDatVe(DatVeRequest datVeRequest);
    List<DonDatVeResponse> getAllDonDat();

    List<DonDatVeResponse> getMyBooking();

    List<DonDatVeResponse> getDonDatVeByIdChuyenXe(Integer id);

    void huyDon(Integer maDonDatVe);


    Optional<DonDatVeResponse> traCuuDonDat(FindingRequest findingRequest);



    Page<DonDatVeResponse> getAllDonDatVeByTrangThai(TrangThaiDonDat trangThai, Pageable pageable);

    Page<DonDatVeResponse> searchDonDatVe(String keyword, Pageable pageable, TrangThaiDonDat trangThaiDonDat);
}
