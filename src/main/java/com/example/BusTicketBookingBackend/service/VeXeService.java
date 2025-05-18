package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.DatVeRequest;
import com.example.BusTicketBookingBackend.dtos.response.VeXeResponse;

import java.util.List;

public interface VeXeService {

    String taoVeXeChoChuyenXe(DatVeRequest datVeRequest, Integer maDonDat);
    List<VeXeResponse> layDanhSachVeXeTheoMaDonDat(Integer maDonDat);

    void huyTatCaVeCuaDonDat(Integer maDonDat);

    Integer huyVeXe(Integer maVeXe);
}
