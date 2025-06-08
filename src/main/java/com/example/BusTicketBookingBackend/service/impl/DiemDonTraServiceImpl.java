package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.DiemDonTraRequest;
import com.example.BusTicketBookingBackend.models.DiemDonTra;
import com.example.BusTicketBookingBackend.models.TinhThanh;
import com.example.BusTicketBookingBackend.repositories.DiemDonTraRepository;
import com.example.BusTicketBookingBackend.repositories.TinhThanhRepository;
import com.example.BusTicketBookingBackend.service.DiemDonTraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DiemDonTraServiceImpl implements DiemDonTraService {

    private final DiemDonTraRepository diemDonTraRepository;
    private final TinhThanhRepository tinhThanhRepository;



    @Override
    public List<DiemDonTra> getDiemDonTraByTinhThanh(String tinhThanh) {
        return diemDonTraRepository.findByTinhThanh_TenTinhThanhAndTrangThai(tinhThanh,1);
    }

    @Override
    public DiemDonTra deleteDiemDonTra(Integer id) {
        DiemDonTra diemDonTra = diemDonTraRepository.findById(id).get();
        diemDonTra.setTrangThai(0);
        diemDonTraRepository.save(diemDonTra);
        return diemDonTra;
    }

    @Override
    public DiemDonTra addDiemDonTra(DiemDonTraRequest diemDonTra) {
        DiemDonTra diemDonTraMoi = new DiemDonTra();
        diemDonTraMoi.setTenDiemDon(diemDonTra.getTenDiemDonTra());
        diemDonTraMoi.setDiaChi(diemDonTra.getDiaChi());
        diemDonTraMoi.setTinhThanh(tinhThanhRepository.findById(diemDonTra.getMaTinh()).get());
        diemDonTraMoi.setTrangThai(1);
        diemDonTraRepository.save(diemDonTraMoi);
        return diemDonTraMoi;
    }
}
