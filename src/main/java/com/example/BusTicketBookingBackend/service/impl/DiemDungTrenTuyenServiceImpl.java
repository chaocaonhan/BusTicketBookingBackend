package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.response.DiemDungTrenTuyenDTO;
import com.example.BusTicketBookingBackend.models.DiemDungTrenTuyen;
import com.example.BusTicketBookingBackend.repositories.DiemDungTrenTuyenRepository;
import com.example.BusTicketBookingBackend.service.DiemDungTrenTuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiemDungTrenTuyenServiceImpl implements DiemDungTrenTuyenService {
    private final DiemDungTrenTuyenRepository ddttRepository;

    @Override
    public List<DiemDungTrenTuyenDTO> danhSachDiemDungCuaMotTuyen(Integer idTuyen) {
        List<DiemDungTrenTuyen> lstDiemDung = ddttRepository.findByTuyenId(idTuyen);

        if (lstDiemDung == null || lstDiemDung.isEmpty()) {
            return new ArrayList<>();
        }
        List<DiemDungTrenTuyenDTO> dtoList = new ArrayList<>();

        for (DiemDungTrenTuyen diemDung : lstDiemDung) {
            DiemDungTrenTuyenDTO dto = new DiemDungTrenTuyenDTO();
            dto.setId(diemDung.getId());
            dto.setTenTuyenXe(diemDung.getTuyenXe().getTenTuyen());
            dto.setTenDiemDon(diemDung.getDiemDonTra().getTenDiemDon());
            dto.setDiaChi(diemDung.getDiemDonTra().getDiaChi());
            dto.setThuTuDiemDung(diemDung.getThuTuDiemDung());
            dto.setKhoangCachToiDiemDau(diemDung.getKhoangCachToiDiemDau());
            dto.setThoiGianTuDiemDau(diemDung.getThoiGianTuDiemDau());

            dtoList.add(dto);
        }

        return dtoList;
    }
}
