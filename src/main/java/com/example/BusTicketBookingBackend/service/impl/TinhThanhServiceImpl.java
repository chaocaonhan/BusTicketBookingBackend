package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.models.TinhThanh;
import com.example.BusTicketBookingBackend.repositories.TinhThanhRepository;
import com.example.BusTicketBookingBackend.service.TinhThanhService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TinhThanhServiceImpl implements TinhThanhService {

    private final TinhThanhRepository tinhThanhRepository;

    @Override
    public List<TinhThanh> getAllTinhThanh() {
        return tinhThanhRepository.findAll();
    }

    @Override
    public TinhThanh createTinhThanh(String tinhThanh) {
        TinhThanh tinhMoi = new TinhThanh();
        tinhMoi.setTenTinhThanh(tinhThanh);
        return tinhThanhRepository.save(tinhMoi);

    }

    @Override
    public String deleteTinhThanh(Integer IDtinhThanh) {
        tinhThanhRepository.deleteById(IDtinhThanh);
        return "Đã xoá tỉnh";
    }
}
