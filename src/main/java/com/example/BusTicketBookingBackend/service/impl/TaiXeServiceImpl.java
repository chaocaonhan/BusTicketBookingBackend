package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.response.CaLamViec;
import com.example.BusTicketBookingBackend.dtos.response.LichLamViecResponse;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.ChuyenXe;
import com.example.BusTicketBookingBackend.models.TaiXe;
import com.example.BusTicketBookingBackend.repositories.ChuyenXeRepository;
import com.example.BusTicketBookingBackend.repositories.TaiXeRepository;
import com.example.BusTicketBookingBackend.service.TaiXeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaiXeServiceImpl implements TaiXeService {
    TaiXeRepository taiXeRepository;
    ChuyenXeRepository chuyenXeRepository;

    @Override
    public List<TaiXe> getAvailableTaiXe(LocalDate ngayKhoiHanh, LocalTime gioKhoiHanh) {
        return taiXeRepository.findAvailableDrivers(ngayKhoiHanh, gioKhoiHanh);
    }

    @Override
    public List<TaiXe> getAllTaiXe() {
        return taiXeRepository.findAll();
    }

    @Override
    public void updateAnh(String url, Integer id) {
        TaiXe taiXe = taiXeRepository.findById(id).get();
        if (taiXe == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);

        }
        taiXe.setAnh(url);
        taiXeRepository.save(taiXe);
    }

    @Override
    public List<LichLamViecResponse> getLichLamViec(Integer idTaiXe, LocalDate ngayYeuCau) {
        LocalDate ngayDauTuan = ngayYeuCau.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate ngayCuoiTuan   = ngayYeuCau.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<ChuyenXe> dsChuyen = chuyenXeRepository
                .findByTaiXeIdAndNgayKhoiHanhBetween(idTaiXe, ngayDauTuan, ngayCuoiTuan);

        Map<LocalDate, List<CaLamViec>> grouped = dsChuyen.stream()
                .collect(Collectors.groupingBy(
                        ChuyenXe::getNgayKhoiHanh,
                        Collectors.mapping(this::mapToCaLamViec, Collectors.toList())
                ));


        return grouped.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new LichLamViecResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private CaLamViec mapToCaLamViec(ChuyenXe cx) {
        CaLamViec caLamViec = new CaLamViec();
        caLamViec.setTenTuyen(cx.getTuyenXe().getTenTuyen());
        caLamViec.setThoiGian(cx.getGioKhoiHanh().toString() + " - " + cx.getGioKetThuc().toString());
        caLamViec.setXe(cx.getXe().getLoaiXe().getTenLoaiXe() + "-" + cx.getXe().getBienSo());
        return caLamViec;
    }

    }

