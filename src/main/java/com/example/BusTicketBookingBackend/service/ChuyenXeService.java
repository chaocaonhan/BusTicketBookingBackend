package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.response.ChuyenXeDTO;
import com.example.BusTicketBookingBackend.models.ChuyenXe;

import java.time.LocalDate;
import java.util.List;

public interface ChuyenXeService {
    List<ChuyenXe> timChuyenXeTheoTuyen(String tinhDi, String tinhDen, LocalDate ngaydi);
    String taoChuyenXe(ChuyenXeDTO chuyenXe);
}
