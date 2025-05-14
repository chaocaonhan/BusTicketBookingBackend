package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.ChangePassRequest;
import com.example.BusTicketBookingBackend.dtos.request.LoginDTO;
import com.example.BusTicketBookingBackend.dtos.response.NguoiDungDTO;
import com.example.BusTicketBookingBackend.models.NguoiDung;
import com.example.BusTicketBookingBackend.models.TaiXe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface NguoiDungService {
    List<NguoiDungDTO> getAllNguoiDungNoPaging();

    Page<NguoiDungDTO> getAllNguoiDung(Pageable pageable);

    Page<NguoiDungDTO> searchNguoiDung(String keyword, Pageable pageable);

    NguoiDungDTO createNguoiDung(NguoiDungDTO nguoiDungDTO);
    NguoiDung getNguoiDung(int id);

    NguoiDung setNguoiDung(NguoiDung nguoiDung);
    Optional<NguoiDungDTO> getNguoiDungByID(int id);

    List<TaiXe> getAllTaiXe();

    NguoiDungDTO updateNguoiDung(Integer id, NguoiDungDTO nguoiDungDTO);


    Boolean verifyUser(Integer id, String confirmtoken);
    String login(LoginDTO loginDTO);

    NguoiDungDTO getMyInfor();
    Boolean deleteNguoiDungById(Integer id);

    String changePassword(Integer id, ChangePassRequest changePassRequest);
}
