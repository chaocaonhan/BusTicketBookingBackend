package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.KhuyenMaiDTO;
import com.example.BusTicketBookingBackend.models.KhuyenMai;
import com.example.BusTicketBookingBackend.repositories.KhuyenMaiRepository;
import com.example.BusTicketBookingBackend.service.KhuyenMaiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        km.setPhanTramGiam(khuyenMai.getPhanTramGiam());
        km.setNgayBatDau(khuyenMai.getNgayBatDau());
        km.setNgayKetThuc(khuyenMai.getNgayKetThuc());

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
}
