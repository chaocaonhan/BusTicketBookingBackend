package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.response.XeDTO;
import com.example.BusTicketBookingBackend.models.Xe;

import java.util.List;

public interface XeService {
    public List<XeDTO> getAllXe();
 }
