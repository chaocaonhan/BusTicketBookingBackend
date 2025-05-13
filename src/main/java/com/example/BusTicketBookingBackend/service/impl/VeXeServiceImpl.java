package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeVaGheCanDat;
import com.example.BusTicketBookingBackend.dtos.request.DatVeRequest;
import com.example.BusTicketBookingBackend.dtos.response.VeXeResponse;
import com.example.BusTicketBookingBackend.enums.TrangThaiVe;
import com.example.BusTicketBookingBackend.models.Vexe;
import com.example.BusTicketBookingBackend.repositories.DatGheRepository;
import com.example.BusTicketBookingBackend.repositories.DonDatVeRepository;
import com.example.BusTicketBookingBackend.repositories.VeXeRepository;
import com.example.BusTicketBookingBackend.service.VeXeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VeXeServiceImpl implements VeXeService {
    VeXeRepository veXeRepository;
    DonDatVeRepository donDatVeRepository;
    DatGheRepository datGheRepository;

    @Override
    public String taoVeXeChoChuyenXe(DatVeRequest datVeRequest, Integer maDonDat) {
        ChuyenXeVaGheCanDat chuyenDi = datVeRequest.getChuyenDi();
        if (chuyenDi != null && chuyenDi.getDanhSachGheMuonDat() != null && !chuyenDi.getDanhSachGheMuonDat().isEmpty()) {
            chuyenDi.getDanhSachGheMuonDat().forEach(maGhe -> {
                Vexe veXe = new Vexe();
                veXe.setDonDatVe(donDatVeRepository.findById(maDonDat).orElseThrow(() ->
                        new IllegalArgumentException("Mã đơn đặt vé không hợp lệ: " + maDonDat)
                ));
                veXe.setDatGhe(datGheRepository.findById(maGhe).orElseThrow(() ->
                        new IllegalArgumentException("Không tìm thấy ghế với mã: " + maGhe)
                ));
                veXe.setTrangThaiVe(TrangThaiVe.BOOKED);
                veXeRepository.save(veXe);
            });
        }

        // Kiểm tra và xử lý thông tin "chuyenVe"
        ChuyenXeVaGheCanDat chuyenVe = datVeRequest.getChuyenVe();
        if (chuyenVe != null && chuyenVe.getDanhSachGheMuonDat() != null && !chuyenVe.getDanhSachGheMuonDat().isEmpty()) {
            chuyenVe.getDanhSachGheMuonDat().forEach(maGhe -> {
                Vexe veXe = new Vexe();
                veXe.setDonDatVe(donDatVeRepository.findById(maDonDat).orElseThrow(() ->
                        new IllegalArgumentException("Mã đơn đặt vé không hợp lệ: " + maDonDat)
                ));
                veXe.setDatGhe(datGheRepository.findById(maGhe).orElseThrow(() ->
                        new IllegalArgumentException("Không tìm thấy ghế với mã: " + maGhe)
                ));
                veXe.setTrangThaiVe(TrangThaiVe.BOOKED);
                veXeRepository.save(veXe);
            });
        }

        return "Tạo vé thành công!";
    }

    @Override
    public List<VeXeResponse> layDanhSachVeXeTheoMaDonDat(Integer maDonDat) {
        List<Vexe> veXes = veXeRepository.findAllByDonDatVe_Id(maDonDat);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<VeXeResponse> veXeResponses = veXes.stream().map(vexe -> {
            VeXeResponse response = new VeXeResponse();
            response.setMaVeXe(vexe.getId());
            response.setTenTuyen(vexe.getDatGhe().getChuyenXe().getTuyenXe().getTenTuyen() +" "+ vexe.getDatGhe().getChuyenXe().getNgayKhoiHanh().format(formatter));
            response.setTrangThaiVe(vexe.getTrangThaiVe());
            response.setGiaVe(vexe.getDatGhe().getChuyenXe().getGiaVe());
            response.setLoaiXe(vexe.getDatGhe().getChuyenXe().getXe().getLoaiXe().getTenLoaiXe());
            response.setTenGhe(vexe.getDatGhe().getChoNgoi().getTenghe());
            response.setTrungChuyenTu("Tự di chuyển");
            response.setDiemBatDau(vexe.getDatGhe().getChuyenXe().getDiemDi().getTenDiemDon());
            response.setThoiGianBatDau(vexe.getDatGhe().getChuyenXe().getGioKhoiHanh());
            response.setThoiGianKetThuc(vexe.getDatGhe().getChuyenXe().getGioKetThuc());
            response.setDiemKetThuc(vexe.getDatGhe().getChuyenXe().getDiemDen().getTenDiemDon());
            response.setTrungChuyenDen("Tự di chuyển");
            return response;
        }).toList();
        return veXeResponses;
    }

}

