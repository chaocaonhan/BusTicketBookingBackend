package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.LoginDTO;
import com.example.BusTicketBookingBackend.dtos.NguoiDungDTO;
import com.example.BusTicketBookingBackend.models.NguoiDung;

import java.util.List;
import java.util.Optional;


public interface NguoiDungService {
    String createNguoiDung(NguoiDungDTO nguoiDungDTO);
    NguoiDung getNguoiDung(int id);

    NguoiDung setNguoiDung(NguoiDung nguoiDung);
    Optional<NguoiDungDTO> getNguoiDungByID(int id);
    List<NguoiDungDTO> getAllNguoiDung();
    String updateNguoiDung(NguoiDungDTO nguoiDungDTO);


    Boolean verifyUser(Integer id, String confirmtoken);
    String login(LoginDTO loginDTO);
}
