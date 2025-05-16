package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.response.TuyenXeDTO;
import com.example.BusTicketBookingBackend.models.TuyenXe;

import java.util.List;

public interface TuyenXeService {
    TuyenXe timTuyenTheoTenHaiTinh(String tinhXuatPhat, String tinhDen);
    List<TuyenXe> getAllTuyenXe();

    String editTuyenXe(TuyenXeDTO tuyenXeDTO, Integer id);

    TuyenXe createTuyenXe(TuyenXeDTO tuyenXeDTO);

    String deleteTuyenXe(Integer id);

    List<TuyenXe> getTop5TuyenXePhoBien();
}
