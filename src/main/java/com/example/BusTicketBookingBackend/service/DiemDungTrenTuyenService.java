package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.CapNhatLichTrinhRequest;
import com.example.BusTicketBookingBackend.dtos.response.DiemDungTrenTuyenDTO;

import java.util.List;

public interface DiemDungTrenTuyenService {
    List<DiemDungTrenTuyenDTO> danhSachDiemDungCuaMotTuyen(Integer idTuyen);

    int capnhat(CapNhatLichTrinhRequest capNhatLichTrinhRequest);
}
