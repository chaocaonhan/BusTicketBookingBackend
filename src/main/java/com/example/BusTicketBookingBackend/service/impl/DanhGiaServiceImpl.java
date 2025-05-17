package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.DanhGiaRequest;
import com.example.BusTicketBookingBackend.dtos.response.DanhGiaResponse;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.*;
import com.example.BusTicketBookingBackend.repositories.*;
import com.example.BusTicketBookingBackend.service.ChuyenXeService;
import com.example.BusTicketBookingBackend.service.DanhGiaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DanhGiaServiceImpl implements DanhGiaService {
    DanhGiaRepository danhGiaRepository;
    VeXeRepository veXeRepository;
    ChuyenXeRepository chuyenXeRepository;
    NguoiDungRepository nguoiDungRepository;
    DonDatVeRepository donDatVeRepository;
    ChuyenXeService chuyenXeService;

    @Override
    public String themDanhGia(DanhGiaRequest danhGiaRequest) {
        DonDatVe donDatVeCanDanhGia = donDatVeRepository.findById(danhGiaRequest.getMaDonDatVe())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        DanhGia danhGiaCanTim = danhGiaRepository.findByDonDatVe_Id(donDatVeCanDanhGia.getId());
        if(danhGiaCanTim != null){
            return "Đã đánh giá đơn này rồi";
        }

        var context = SecurityContextHolder.getContext();
        String mail = context.getAuthentication().getName();
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(mail)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));

        DanhGia danhGia = new DanhGia();
        danhGia.setDonDatVe(donDatVeCanDanhGia);
        danhGia.setNguoidung(nguoiDung);
        danhGia.setSoSao(danhGiaRequest.getSoSao());
        danhGia.setNoiDung(danhGiaRequest.getNoiDung());
        danhGia.setCreatedAt(LocalDateTime.now());
        danhGiaRepository.save(danhGia);
        return "Danh gia thanh cong";
    }

    @Override
    public List<DanhGiaResponse> getAllDanhGia(){
        List<DanhGia> danhGias = danhGiaRepository.findAll();
        return danhGias.stream().map(danhGia -> {
            DanhGiaResponse response = new DanhGiaResponse();
            response.setId(danhGia.getId());
            response.setTenKhachHang(danhGia.getNguoidung().getHoTen());
            response.setNoiDung(danhGia.getNoiDung());
            response.setSoSao(danhGia.getSoSao());

            //Lấy thông tiin chuyến được đánh giá
            int  idChuyenXeDuocDanhGia = chuyenXeService.getChuyenXeIdFromDonDatVeId(danhGia.getDonDatVe().getId());
            ChuyenXe chuyenXeDuocDanhGia = chuyenXeRepository.findById(idChuyenXeDuocDanhGia).get();

            response.setTenChuyenXe(chuyenXeDuocDanhGia.getTuyenXe().getTenTuyen()+" "+chuyenXeDuocDanhGia.getNgayKhoiHanh());
            response.setAnhNguoiDung(danhGia.getNguoidung().getAvatar());
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean daDanhGia(Integer maDonDatHang){
        DanhGia danhGia = danhGiaRepository.findByDonDatVe_Id(maDonDatHang);
        if(danhGia != null){
            return true;
        }
        return false;

    }

    @Override
    public DanhGiaResponse getDanhGiaByMaDonDatVe(Integer maDonDatVe) {
        DanhGia danhGia = danhGiaRepository.findByDonDatVe_Id(maDonDatVe);
        if (danhGia == null) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }

        DanhGiaResponse response = new DanhGiaResponse();
        response.setId(danhGia.getId());
        response.setTenKhachHang(danhGia.getNguoidung().getHoTen());
        response.setNoiDung(danhGia.getNoiDung());
        response.setSoSao(danhGia.getSoSao());

        // Lấy thông tin chuyến được đánh giá
        int idChuyenXeDuocDanhGia = chuyenXeService.getChuyenXeIdFromDonDatVeId(danhGia.getDonDatVe().getId());
        ChuyenXe chuyenXeDuocDanhGia = chuyenXeRepository.findById(idChuyenXeDuocDanhGia)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        response.setTenChuyenXe(chuyenXeDuocDanhGia.getTuyenXe().getTenTuyen() + " " + chuyenXeDuocDanhGia.getNgayKhoiHanh());
        response.setAnhNguoiDung(danhGia.getNguoidung().getAvatar());

        return response;
    }


}
