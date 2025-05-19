package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.DatVeRequest;
import com.example.BusTicketBookingBackend.dtos.response.DonDatVeResponse;
import com.example.BusTicketBookingBackend.models.DonDatVe;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DonDatVeService {
    String taoDonDatVe(DatVeRequest datVeRequest);
    List<DonDatVeResponse> getAllDonDat();

    List<DonDatVeResponse> getMyBooking();

    void huyDon(Integer maDonDatVe);

}
