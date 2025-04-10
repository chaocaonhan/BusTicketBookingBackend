package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.models.DiemDonTra;
import com.example.BusTicketBookingBackend.repositories.DiemDonTraRepository;
import com.example.BusTicketBookingBackend.service.DiemDonTraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DiemDonTraServiceImpl implements DiemDonTraService {

    private final DiemDonTraRepository diemDonTraRepository;

    @Override
    public List<DiemDonTra> getAllDiemDonTra() {
        return diemDonTraRepository.findAll();
    }

    @Override
    public List<DiemDonTra> getDiemDonTraByTinhThanh(String tinhThanh) {
        return diemDonTraRepository.findByTinhThanh_TenTinhThanh(tinhThanh);
    }
}
