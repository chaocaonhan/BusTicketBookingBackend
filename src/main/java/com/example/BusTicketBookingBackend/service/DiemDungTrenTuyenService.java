package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.CapNhatLichTrinhRequest;
import com.example.BusTicketBookingBackend.dtos.response.DiemDungTrenTuyenDTO;
import com.example.BusTicketBookingBackend.models.DiemDonTra;
import com.example.BusTicketBookingBackend.models.DiemDungTrenTuyen;

import java.util.List;

public interface DiemDungTrenTuyenService {
    List<DiemDungTrenTuyenDTO> danhSachDiemDungCuaMotTuyen(Integer idTuyen);

    int capnhat(CapNhatLichTrinhRequest capNhatLichTrinhRequest);

    DiemDonTra getDiemDau(int tuyenXeId);

    DiemDonTra getDiemCuoi(int tuyenXeId);
}
