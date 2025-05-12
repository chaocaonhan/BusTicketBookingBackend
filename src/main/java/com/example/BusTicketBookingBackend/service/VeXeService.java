package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.DatVeRequest;

public interface VeXeService {
    String taoVeXeChoChuyenXe(DatVeRequest datVeRequest, Integer maDonDat);
}
