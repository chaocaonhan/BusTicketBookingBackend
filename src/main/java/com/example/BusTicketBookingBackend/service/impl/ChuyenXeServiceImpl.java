package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.response.ChuyenXeDTO;
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
    XeRepository xeRepository;
    TaiXeRepository taiXeRepository;

    @Override
    public List<ChuyenXe> timChuyenXeTheoTuyen(String tinhDi, String tinhDen,LocalDate ngayDi) {

        Optional<TuyenXe> tuyenCanTim = tuyenXeRepository.findByTinhDiAndTinhDen(tinhDi, tinhDen);

        if (tuyenCanTim.isPresent()) {
            TuyenXe tuyenXe = tuyenCanTim.get();

            List<ChuyenXe> lstChuyenXe = chuyenXeRepository.findChuyenXeByTuyenXe(tuyenXe)
                    .stream()
                    .filter(chuyenXe -> {
                        LocalDate ngayKhoiHanh = chuyenXe.getNgayKhoiHanh();
                        LocalTime gioKhoiHanh = chuyenXe.getGioKhoiHanh();

                        // Chỉ lấy chuyến xe có ngày khởi hành trùng với ngày đi do client gửi
                        if (ngayKhoiHanh.isEqual(ngayDi)) {
                            // Nếu ngày đi là hôm nay, kiểm tra giờ khởi hành phải sau giờ hiện tại
                            return ngayDi.isAfter(LocalDate.now()) || gioKhoiHanh.isAfter(LocalTime.now());
                        }
                        return false;
                    })
                    .toList();

            if (lstChuyenXe.isEmpty()) {
                throw new RuntimeException("Không có chuyến xe nào phù hợp.");
            }

            return lstChuyenXe;
        } else {
            throw new RuntimeException("Không tìm thấy tuyến xe phù hợp.");
        }
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
