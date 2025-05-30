package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.models.TaiXe;
import com.example.BusTicketBookingBackend.repositories.TaiXeRepository;
import com.example.BusTicketBookingBackend.service.TaiXeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaiXeServiceImpl implements TaiXeService {
    TaiXeRepository taiXeRepository;

    @Override
    public List<TaiXe> getAvailableTaiXe(LocalDate ngayKhoiHanh, LocalTime gioKhoiHanh) {
        return taiXeRepository.findAvailableDrivers(ngayKhoiHanh, gioKhoiHanh);
    }
}
