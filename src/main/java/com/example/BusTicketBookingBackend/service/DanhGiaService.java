package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.DanhGiaRequest;
import com.example.BusTicketBookingBackend.dtos.response.DanhGiaResponse;
import com.example.BusTicketBookingBackend.models.DanhGia;

import java.util.List;

public interface DanhGiaService {
    String themDanhGia(DanhGiaRequest danhGiaRequest);

    List<DanhGiaResponse> getAllDanhGia();

    boolean daDanhGia(Integer maDonDatHang);

    DanhGiaResponse getDanhGiaByMaDonDatVe(Integer maDonDatVe);
}
