package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeDTO;
import com.example.BusTicketBookingBackend.dtos.response.ChuyenXeResponse;
import com.example.BusTicketBookingBackend.enums.TrangThaiGhe;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.*;
import com.example.BusTicketBookingBackend.repositories.*;
import com.example.BusTicketBookingBackend.service.ChuyenXeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private final ChoNgoiRepository choNgoiRepository;
    private final DatGheRepository datGheRepository;

    public List<ChuyenXeResponse> timChuyenXeTheoTuyen(String tinhDi, String tinhDen, LocalDate ngayDi, LocalDate ngayVe, Boolean khuHoi) {
        Optional<TuyenXe> tuyenDi = tuyenXeRepository.findByTinhDiAndTinhDen(tinhDi, tinhDen);
        Optional<TuyenXe> tuyenVe = tuyenXeRepository.findByTinhDiAndTinhDen(tinhDen, tinhDi);

        if (tuyenDi.isEmpty()) {
            throw new RuntimeException("Không tìm thấy tuyến xe phù hợp.");
        }

        List<ChuyenXe> lstChuyenDi = chuyenXeRepository.findChuyenXeByTuyenXe(tuyenDi.get())
                .stream()
                .filter(chuyenXe -> {
                    LocalDate ngayKhoiHanh = chuyenXe.getNgayKhoiHanh();
                    LocalTime gioKhoiHanh = chuyenXe.getGioKhoiHanh();
                    return ngayKhoiHanh.isEqual(ngayDi)
                            && (ngayDi.isAfter(LocalDate.now()) || gioKhoiHanh.isAfter(LocalTime.now()));
                })
                .toList();

        List<ChuyenXe> lstChuyenVe = new ArrayList<>();
        if (Boolean.TRUE.equals(khuHoi)) {
            if (tuyenVe.isEmpty()) {
                throw new RuntimeException("Không tìm thấy chuyến xe chiều về");
            }
            lstChuyenVe = chuyenXeRepository.findChuyenXeByTuyenXe(tuyenVe.get())
                    .stream()
                    .filter(chuyenXe -> {
                        LocalDate ngayKhoiHanh = chuyenXe.getNgayKhoiHanh();
                        LocalTime gioKhoiHanh = chuyenXe.getGioKhoiHanh();
                        return ngayKhoiHanh.isEqual(ngayVe)
                                && (ngayVe.isAfter(LocalDate.now()) || gioKhoiHanh.isAfter(LocalTime.now()));
                    })
                    .toList();
        }

        if (lstChuyenDi.isEmpty() && lstChuyenVe.isEmpty()) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }

        // Gộp 2 list chuyến đi và về
        List<ChuyenXe> allChuyenXe = new ArrayList<>();
        allChuyenXe.addAll(lstChuyenDi);
        allChuyenXe.addAll(lstChuyenVe);

        return allChuyenXe.stream()
                .map(this::convertToResponse)
                .toList();
    }


    @Override
    public List<ChuyenXeResponse> getAll() {
        List<ChuyenXe> lsChuyenXe = chuyenXeRepository.findAll();
        return lsChuyenXe.stream().map(this::convertToResponse).toList();
    }

    @Override
    public ChuyenXeResponse editChuyenXe(ChuyenXeDTO chuyenXeDto, Integer idChuyenXe) {
        ChuyenXe chuyenXe = chuyenXeRepository.findById(idChuyenXe).get();
        if (chuyenXe == null) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }

        chuyenXe.setTuyenXe(tuyenXeRepository.findByTenTuyen(chuyenXeDto.getTenTuyen()));
        chuyenXe.setXe(xeRepository.findByBienSo(chuyenXeDto.getBienSoXe()));
        chuyenXe.setTaiXe(taiXeRepository.findByHoTen(chuyenXeDto.getTaiXe()));
        chuyenXe.setDiemDi(diemDonTraRepository.findDiemDonTraByTenDiemDon(chuyenXeDto.getDiemDi()));
        chuyenXe.setDiemDen(diemDonTraRepository.findDiemDonTraByTenDiemDon(chuyenXeDto.getDiemDen()));
        chuyenXe.setNgayKhoiHanh(chuyenXeDto.getNgayKhoiHanh());
        chuyenXe.setGioKhoiHanh(chuyenXeDto.getGioKhoiHanh());
        chuyenXe.setGioKetThuc(chuyenXeDto.getGioKetThuc());
        chuyenXe.setGiaVe(chuyenXeDto.getGiaVe());

        return convertToResponse(chuyenXeRepository.save(chuyenXe));



    }

    private ChuyenXeResponse convertToResponse(ChuyenXe chuyenXe) {
        return ChuyenXeResponse.builder()
                .id(chuyenXe.getId())
                .tenTuyen(chuyenXe.getTuyenXe().getTenTuyen())
                .diemDi(chuyenXe.getDiemDi().getTenDiemDon())
                .diemDen(chuyenXe.getDiemDen().getTenDiemDon())
                .ngayKhoiHanh(chuyenXe.getNgayKhoiHanh())
                .gioKhoiHanh(chuyenXe.getGioKhoiHanh())
                .gioKetThuc(chuyenXe.getGioKetThuc())
                .bienSo(chuyenXe.getXe().getBienSo())
                .taiXe(chuyenXe.getTaiXe().getHoTen())
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

        List<ChoiNgoi> choiNgois = choNgoiRepository.findByLoaiXe_Id(loaiXe.getId());

        chuyenXeRepository.save(cx);

        for (ChoiNgoi cn : choiNgois) {
            DatGhe datGhes = new DatGhe();
            datGhes.setChuyenXe(cx);
            datGhes.setChoNgoi(cn);
            datGhes.setTrangThai(TrangThaiGhe.AVAILABLE);
            datGheRepository.save(datGhes);
        }



        return "Thêm chuyến xe thành công";
    }


}
