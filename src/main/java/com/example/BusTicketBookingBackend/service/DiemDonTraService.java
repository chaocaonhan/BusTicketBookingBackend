package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.DiemDonTraRequest;
import com.example.BusTicketBookingBackend.models.DiemDonTra;

import java.util.List;

public interface DiemDonTraService {
    List<DiemDonTra> getAllDiemDonTra();
    List<DiemDonTra> getDiemDonTraByTinhThanh(String tinhThanh);
    DiemDonTra deleteDiemDonTra(Integer id);
    DiemDonTra addDiemDonTra(DiemDonTraRequest diemDonTra);
}
