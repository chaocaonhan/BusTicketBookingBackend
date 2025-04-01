package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.models.TinhThanh;

import java.util.List;

public interface TinhThanhService {
    List<TinhThanh> getAllTinhThanh();
    TinhThanh createTinhThanh(String tinhThanh);
    String deleteTinhThanh(Integer IdtinhThanh);
}
