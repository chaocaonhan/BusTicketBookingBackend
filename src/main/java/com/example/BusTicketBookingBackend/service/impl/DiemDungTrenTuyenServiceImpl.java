package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.CapNhatLichTrinhRequest;
import com.example.BusTicketBookingBackend.dtos.response.DiemDungTrenTuyenDTO;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.DiemDungTrenTuyen;
import com.example.BusTicketBookingBackend.models.TuyenXe;
import com.example.BusTicketBookingBackend.repositories.DiemDonTraRepository;
import com.example.BusTicketBookingBackend.repositories.DiemDungTrenTuyenRepository;
import com.example.BusTicketBookingBackend.repositories.TuyenXeRepository;
import com.example.BusTicketBookingBackend.service.DiemDungTrenTuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiemDungTrenTuyenServiceImpl implements DiemDungTrenTuyenService {
    private final DiemDungTrenTuyenRepository ddttRepository;
    private final TuyenXeRepository tuyenXeRepository;
    private final DiemDonTraRepository diemDonTraRepository;

    @Override
    public List<DiemDungTrenTuyenDTO> danhSachDiemDungCuaMotTuyen(Integer idTuyen) {
        List<DiemDungTrenTuyen> lstDiemDung = ddttRepository.findByTuyenId(idTuyen);

        if (lstDiemDung == null || lstDiemDung.isEmpty()) {
            return new ArrayList<>();
        }
        List<DiemDungTrenTuyenDTO> dtoList = new ArrayList<>();

        for (DiemDungTrenTuyen diemDung : lstDiemDung) {
            if(diemDung.getTrangThai() == 1){
                DiemDungTrenTuyenDTO dto = new DiemDungTrenTuyenDTO();
                dto.setId(diemDung.getId());
                dto.setTenTuyenXe(diemDung.getTuyenXe().getTenTuyen());
                dto.setTenDiemDon(diemDung.getDiemDonTra().getTenDiemDon());
                dto.setIdDiemDonTra(diemDung.getDiemDonTra().getId());
                dto.setDiaChi(diemDung.getDiemDonTra().getDiaChi());
                dto.setThuTuDiemDung(diemDung.getThuTuDiemDung());
                dto.setKhoangCachToiDiemDau(diemDung.getKhoangCachToiDiemDau());
                dto.setThoiGianTuDiemDau(diemDung.getThoiGianTuDiemDau());

                dtoList.add(dto);
            }
        }

        return dtoList;
    }

    @Override
    public int capnhat(CapNhatLichTrinhRequest capNhatLichTrinhRequest) {
        Optional<TuyenXe> timtuyen = tuyenXeRepository.findById(capNhatLichTrinhRequest.getIdTuyen());
        TuyenXe tuyenCanSua = timtuyen.orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));


        List<DiemDungTrenTuyen> lstDiemDung = ddttRepository.findByTuyenId(capNhatLichTrinhRequest.getIdTuyen());
        for (DiemDungTrenTuyen diemDung : lstDiemDung) {
            diemDung.setTrangThai(0);
            ddttRepository.save(diemDung);
        }

        List<Integer> lichTrinhMoi = capNhatLichTrinhRequest.getDanhSachDiemDonTheoThuTu();
        for (Integer diemDon : lichTrinhMoi) {
            int i =1;
            DiemDungTrenTuyen diemDonMoi = new DiemDungTrenTuyen();
            diemDonMoi.setThuTuDiemDung(i);
            diemDonMoi.setTuyenXe(tuyenCanSua);
            diemDonMoi.setDiemDonTra(diemDonTraRepository.findById(diemDon).get());
            diemDonMoi.setTrangThai(1);

            //xử lý khoảng cách vs tgian sau bằng google
            ddttRepository.save(diemDonMoi);
            i++;
        }
        return 1;
    }
}
