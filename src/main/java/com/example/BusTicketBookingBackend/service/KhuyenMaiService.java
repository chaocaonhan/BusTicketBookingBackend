package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.KhuyenMaiDTO;
import com.example.BusTicketBookingBackend.models.KhuyenMai;

public interface KhuyenMaiService {
    KhuyenMai createKhuyenMai(KhuyenMaiDTO khuyenMai);
    int checkKhuyenMai(String khuyenMai);

    KhuyenMai edit(int id, KhuyenMaiDTO khuyenMaiDTO);

    Object deleteKM(int id);
}
