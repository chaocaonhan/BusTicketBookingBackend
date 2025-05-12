package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeVaGheCanDat;
import com.example.BusTicketBookingBackend.dtos.response.DatGheResponse;

import java.util.List;

public interface DatGheService {
    List<DatGheResponse> getByChuyenXe(Integer idChuyenXe);


    boolean seatSelectedIsAvaible(List<Integer> datgheIds);

    boolean capNhatTrangThaiGhe(ChuyenXeVaGheCanDat chuyenDi);
}
