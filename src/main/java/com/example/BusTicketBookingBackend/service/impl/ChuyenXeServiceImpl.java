package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeDTO;
import com.example.BusTicketBookingBackend.dtos.response.ChuyenXeResponse;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.ChuyenXe;
import com.example.BusTicketBookingBackend.models.LoaiXe;
import com.example.BusTicketBookingBackend.models.TuyenXe;
import com.example.BusTicketBookingBackend.repositories.*;
import com.example.BusTicketBookingBackend.service.ChuyenXeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChuyenXeServiceImpl implements ChuyenXeService {
    private final ChuyenXeRepository chuyenXeRepository;
    private final TuyenXeRepository tuyenXeRepository;
    private final DiemDonTraRepository diemDonTraRepository;
    private final XeRepository xeRepository;
    private final TaiXeRepository taiXeRepository;

    @Override
    public List<ChuyenXeResponse> timChuyenXeTheoTuyen(String tinhDi, String tinhDen, LocalDate ngayDi) {
        Optional<TuyenXe> tuyenCanTim = tuyenXeRepository.findByTinhDiAndTinhDen(tinhDi, tinhDen);

        if (tuyenCanTim.isEmpty()) {
            throw new RuntimeException("Không tìm thấy tuyến xe phù hợp.");
        }

        List<ChuyenXe> lstChuyenXe = chuyenXeRepository.findChuyenXeByTuyenXe(tuyenCanTim.get())
                .stream()
                .filter(chuyenXe -> {
                    LocalDate ngayKhoiHanh = chuyenXe.getNgayKhoiHanh();
                    LocalTime gioKhoiHanh = chuyenXe.getGioKhoiHanh();

                    return ngayKhoiHanh.isEqual(ngayDi)
                            && (ngayDi.isAfter(LocalDate.now()) || gioKhoiHanh.isAfter(LocalTime.now()));
                })
                .toList();

        if (lstChuyenXe.isEmpty()) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }

        return lstChuyenXe.stream().map(this::convertToResponse).toList();
    }

    @Override
    public List<ChuyenXeResponse> getAll() {
        List<ChuyenXe> lsChuyenXe = chuyenXeRepository.findAll();
        return lsChuyenXe.stream().map(this::convertToResponse).toList();
    }

    private ChuyenXeResponse convertToResponse(ChuyenXe chuyenXe) {
        return ChuyenXeResponse.builder()
                .diemDi(chuyenXe.getDiemDi().getTenDiemDon())
                .diemDen(chuyenXe.getDiemDen().getTenDiemDon())
                .gioKhoiHanh(chuyenXe.getGioKhoiHanh())
                .gioKetThuc(chuyenXe.getGioKetThuc())
                .giaVe(chuyenXe.getGiaVe())
                .tenLoaiXe(chuyenXe.getXe().getLoaiXe().getTenLoaiXe())
                .soGheTrong(chuyenXe.getSoGheTrong())
                .build();
    }


    @Override
    public String taoChuyenXe(ChuyenXeDTO chuyenXe) {
        ChuyenXe cx = new ChuyenXe();
        cx.setTuyenXe(tuyenXeRepository.findByTenTuyen(chuyenXe.getTenTuyen()));
        cx.setXe(xeRepository.findByBienSo(chuyenXe.getBienSoXe()));
        cx.setTaiXe(taiXeRepository.findByHoTen(chuyenXe.getTaiXe()));
        cx.setDiemDi(diemDonTraRepository.findDiemDonTraByTenDiemDon(chuyenXe.getDiemDi()));
        cx.setDiemDen(diemDonTraRepository.findDiemDonTraByTenDiemDon(chuyenXe.getDiemDen()));
        cx.setNgayKhoiHanh(chuyenXe.getNgayKhoiHanh());
        cx.setGioKhoiHanh(chuyenXe.getGioKhoiHanh());
        cx.setGioKetThuc(chuyenXe.getGioKetThuc());
        cx.setGiaVe(chuyenXe.getGiaVe());

        LoaiXe loaiXe = xeRepository.findByBienSo(chuyenXe.getBienSoXe()).getLoaiXe();
        int soGhe = loaiXe.getSoLuongGhe();
        cx.setSoGheTrong(soGhe);
        cx.setTrangThai(ChuyenXe.TrangThai.SCHEDULED);

        chuyenXeRepository.save(cx);

        return "Thêm chuyến xe thành công";
    }


}
