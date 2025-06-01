package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.request.DatVeRequest;
import com.example.BusTicketBookingBackend.dtos.response.VeXeResponse;
import com.example.BusTicketBookingBackend.enums.TrangThaiDonDat;

import java.util.List;

public interface VeXeService {

    String taoVeXeChoChuyenXe(DatVeRequest datVeRequest, Integer maDonDat);
    List<VeXeResponse> layDanhSachVeXeTheoMaDonDat(Integer maDonDat);

    void huyTatCaVeCuaDonDat(Integer maDonDat);

    TrangThaiDonDat kiemTraTrangDonDat(Integer maDonDat);

    Integer huyVeXe(Integer maVeXe);

    void huyVeTheoChuyenXe(Integer chuyenXeId);

    void capNhatTrangThaiVeKhiHoanThanhChuyen(Integer chuyenXeId);
}
