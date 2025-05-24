package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeDTO;
import com.example.BusTicketBookingBackend.dtos.response.ChuyenXeResponse;
import com.example.BusTicketBookingBackend.dtos.response.DiemDonCuaChuyen;

import java.time.LocalDate;
import java.util.List;

public interface ChuyenXeService {
    List<ChuyenXeResponse> timChuyenXeTheoTuyen(int tinhDi, int tinhDen, LocalDate ngaydi, LocalDate ngayVe, Boolean khuHoi);

    ChuyenXeResponse editChuyenXe(ChuyenXeDTO chuyenXeDto, Integer idChuyenXe);

    String taoChuyenXe(ChuyenXeDTO chuyenXe);

    void upDateSoGheTrong(int maChuyenXe);

    List<ChuyenXeResponse> getAll();

    void capNhatSoGheTrong(Integer idChuyenXe);
    List<DiemDonCuaChuyen> getLichTrinhChuyenXe(Integer idChuyenXe);

    int getChuyenXeIdFromDonDatVeId(int donDatVeId);

    int huyChuyen(int idChuyenXe);
}
