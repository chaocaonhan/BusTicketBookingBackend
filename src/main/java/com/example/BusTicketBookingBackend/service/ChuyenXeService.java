package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeDTO;
import com.example.BusTicketBookingBackend.dtos.response.ChuyenXeResponse;

import java.time.LocalDate;
import java.util.List;

public interface ChuyenXeService {
    List<ChuyenXeResponse> timChuyenXeTheoTuyen(String tinhDi, String tinhDen, LocalDate ngaydi);
    String taoChuyenXe(ChuyenXeDTO chuyenXe);
    List<ChuyenXeResponse> getAll();
}
