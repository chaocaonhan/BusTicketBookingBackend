package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.KhuyenMaiDTO;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.KhuyenMai;
import com.example.BusTicketBookingBackend.repositories.KhuyenMaiRepository;
import com.example.BusTicketBookingBackend.service.KhuyenMaiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KhuyenMaiServiceImpl implements KhuyenMaiService {

    KhuyenMaiRepository khuyenMaiRepository;

    @Override
    public KhuyenMai createKhuyenMai(KhuyenMaiDTO khuyenMai) {
        KhuyenMai km = new KhuyenMai();
        km.setMaKhuyenMai(khuyenMai.getMaKhuyenMai());
        km.setMoTa(khuyenMai.getMoTa());
        km.setPhanTramGiam(khuyenMai.getPhanTramGiam());
        km.setNgayBatDau(khuyenMai.getNgayBatDau());
        km.setNgayKetThuc(khuyenMai.getNgayKetThuc());
        km.setTrangThai(1);

        khuyenMaiRepository.save(km);

        return km;
    }

    @Override
    public int checkKhuyenMai(String maKM) {
        List<KhuyenMai> khuyenMais = khuyenMaiRepository.findAll();
        for (KhuyenMai km : khuyenMais) {
            if (km.getMaKhuyenMai().equalsIgnoreCase(maKM)) {
                LocalDate now = LocalDate.now();
                if (now.isAfter(km.getNgayBatDau()) && now.isBefore(km.getNgayKetThuc())) {
                    return km.getPhanTramGiam();
                }
            }
        }
        return 0;
    }

    @Override
    public KhuyenMai edit(int id, KhuyenMaiDTO khuyenMaiDTO){
        KhuyenMai km = khuyenMaiRepository.findById(id).get();
        km.setMoTa(khuyenMaiDTO.getMoTa());
        km.setMaKhuyenMai(khuyenMaiDTO.getMaKhuyenMai());
        km.setPhanTramGiam(khuyenMaiDTO.getPhanTramGiam());
        km.setNgayKetThuc(khuyenMaiDTO.getNgayKetThuc());
        km.setNgayBatDau(khuyenMaiDTO.getNgayBatDau());
        km.setTrangThai(1);


        khuyenMaiRepository.save(km);
        return km;
    }

    @Override
    public String deleteKM(int id) {
        KhuyenMai km = khuyenMaiRepository.findById(id).get();
        if (km.getMaKhuyenMai() == null) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        km.setTrangThai(2);
        khuyenMaiRepository.save(km);

        return "Đã xoá khuyến mại";

    }
}
