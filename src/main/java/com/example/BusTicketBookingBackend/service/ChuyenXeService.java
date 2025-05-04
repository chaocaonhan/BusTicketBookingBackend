package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeDTO;
import com.example.BusTicketBookingBackend.dtos.response.ChuyenXeResponse;

import java.time.LocalDate;
import java.util.List;

public interface ChuyenXeService {
    List<ChuyenXeResponse> timChuyenXeTheoTuyen(String tinhDi, String tinhDen, LocalDate ngaydi, LocalDate ngayVe, Boolean khuHoi);

    ChuyenXeResponse editChuyenXe(ChuyenXeDTO chuyenXeDto, Integer idChuyenXe);

    String taoChuyenXe(ChuyenXeDTO chuyenXe);
    List<ChuyenXeResponse> getAll();
}
