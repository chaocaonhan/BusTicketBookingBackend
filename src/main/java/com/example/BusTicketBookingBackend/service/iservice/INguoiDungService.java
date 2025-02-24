package com.example.BusTicketBookingBackend.service.iservice;

import com.example.BusTicketBookingBackend.dtos.NguoiDungDTO;
import com.example.BusTicketBookingBackend.models.NguoiDung;

import java.util.List;


public interface INguoiDungService {
    NguoiDung createNguoiDung(NguoiDung nguoiDung);
    NguoiDung getNguoiDung(int id);
    List<NguoiDungDTO> getAllNguoiDung();
    NguoiDung updateNguoiDung(NguoiDung nguoiDung);
}
