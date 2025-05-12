package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeVaGheCanDat;
import com.example.BusTicketBookingBackend.dtos.request.DatVeRequest;
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
}
