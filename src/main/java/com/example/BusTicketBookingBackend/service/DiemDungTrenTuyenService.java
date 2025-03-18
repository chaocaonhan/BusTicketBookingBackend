package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.DiemDungTrenTuyenDTO;

import java.util.List;

public interface DiemDungTrenTuyenService {
    List<DiemDungTrenTuyenDTO> danhSachDiemDungCuaMotTuyen(Integer idTuyen);
}
