package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.response.XeDTO;
import com.example.BusTicketBookingBackend.models.Xe;

import java.util.List;
import java.util.Optional;

public interface XeService {
    public List<XeDTO> getAllXe();

    XeDTO suaThongTinXe(Integer idXe, XeDTO xeDTO);

    Object createXe(XeDTO xeDTO);
}
