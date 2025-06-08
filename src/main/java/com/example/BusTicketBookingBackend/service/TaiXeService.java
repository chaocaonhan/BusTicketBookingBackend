package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.response.LichLamViecResponse;
import com.example.BusTicketBookingBackend.models.TaiXe;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TaiXeService {
    public List<TaiXe> getAvailableTaiXe(LocalDate ngayKhoiHanh, LocalTime gioKhoiHanh);
    public List<TaiXe> getAllTaiXe();

    void updateAnh(String url, Integer id);

    List<LichLamViecResponse> getLichLamViec(Integer idTaiXe, LocalDate ngayYeuCau);
}
