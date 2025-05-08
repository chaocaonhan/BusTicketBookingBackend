package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.response.DatGheResponse;
import com.example.BusTicketBookingBackend.models.DatGhe;

import java.util.List;

public interface DatGheService {
    List<DatGheResponse> getByChuyenXe(Integer idChuyenXe);
}
