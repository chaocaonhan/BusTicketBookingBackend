package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeVaGheCanDat;
import com.example.BusTicketBookingBackend.dtos.request.DatVeRequest;
import com.example.BusTicketBookingBackend.dtos.response.DonDatVeResponse;
import com.example.BusTicketBookingBackend.enums.KieuThanhToan;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.DonDatVe;
import com.example.BusTicketBookingBackend.models.NguoiDung;
import com.example.BusTicketBookingBackend.repositories.DatGheRepository;
import com.example.BusTicketBookingBackend.repositories.DonDatVeRepository;
import com.example.BusTicketBookingBackend.repositories.NguoiDungRepository;
import com.example.BusTicketBookingBackend.repositories.VeXeRepository;
import com.example.BusTicketBookingBackend.service.*;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DonDatVeServiceImpl implements DonDatVeService {
    DonDatVeRepository donDatVeRepository;
    NguoiDungRepository nguoiDungRepository;
    DatGheRepository datGheRepository;
    DatGheService datGheService;
    ChuyenXeService chuyenXeService;
    ModelMapper modelMapper;
    VeXeService veXeService;
    VeXeRepository veXeRepository;
    DanhGiaService danhGiaService;
    private final EmailService emailService;

    @Override
    public String taoDonDatVe(DatVeRequest datVeRequest) {
        DonDatVe donDatVe = new DonDatVe();

        if(datVeRequest.getUserId() != null){
            Optional<NguoiDung> nguoiDung = nguoiDungRepository.findById(datVeRequest.getUserId());
            if(nguoiDung.isEmpty()){
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }
            donDatVe.setNguoiDung(nguoiDung.get());
        }

        donDatVe.setThoiGianDat(LocalDateTime.now());
        donDatVe.setTongTien(datVeRequest.getTongTien());
        donDatVe.setKieuThanhToan(KieuThanhToan.valueOf(datVeRequest.getKieuThanhToan()));
        donDatVe.setTrangThaiThanhToan(datVeRequest.getTrangThaiThanhToan());
        donDatVe.setKhuHoi(datVeRequest.getLoaiChuyenDi());
        donDatVe.setTenHanhKhach(datVeRequest.getHoTen());
        int sl = datVeRequest.getChuyenDi().getDanhSachGheMuonDat().size()+(datVeRequest.getChuyenVe() != null ? datVeRequest.getChuyenVe().getDanhSachGheMuonDat().size() : 0);
        donDatVe.setSoLuongVe(sl);
        donDatVe.setSDT(datVeRequest.getSdt());
        donDatVe.setEmail(datVeRequest.getEmail());

        ChuyenXeVaGheCanDat chuyenDi = datVeRequest.getChuyenDi();
        ChuyenXeVaGheCanDat chuyenVe = datVeRequest.getChuyenVe();

        boolean datVeThanhCong = false;

        // Kiểm tra chuyến đi
        if (chuyenDi != null && datGheService.capNhatTrangThaiGhe(chuyenDi)) {
            chuyenXeService.capNhatSoGheTrong(chuyenDi.getIdChuyenXe());

            // Kiểm tra nếu là chuyến khứ hồi và chuyến về tồn tại
            if (datVeRequest.getLoaiChuyenDi() == 1 && chuyenVe != null) {
                if (datGheService.capNhatTrangThaiGhe(chuyenVe)) {
                    chuyenXeService.capNhatSoGheTrong(chuyenVe.getIdChuyenXe());
                    datVeThanhCong = true;
                }
            } else {
                // Nếu không phải chuyến khứ hồi, chỉ cần xử lý chuyến đi là đủ
                datVeThanhCong = true;
            }
        }

        if (datVeThanhCong) {
            donDatVeRepository.save(donDatVe);


            if(donDatVe.getKieuThanhToan() == KieuThanhToan.CASH){
                try {
                    emailService.sendBookingDetailsEmail(donDatVe.getId());
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }

            veXeService.taoVeXeChoChuyenXe(datVeRequest, donDatVe.getId());
            return String.valueOf(donDatVe.getId());
        }
        return "Dat ve that bai";
    }

    @Override
    public List<DonDatVeResponse> getAllDonDat() {

        List<DonDatVe> donDatVes = donDatVeRepository.findAll();
        return donDatVes.stream().map(donDatVe -> {
            DonDatVeResponse donDatVeResponse = modelMapper.map(donDatVe, DonDatVeResponse.class);
            if(donDatVe.getNguoiDung() != null){
                donDatVeResponse.setTenNguoiDat(donDatVe.getNguoiDung().getHoTen());
            }
            donDatVeResponse.setTenHanhKhach(donDatVe.getTenHanhKhach());
            donDatVeResponse.setId(donDatVe.getId());
            donDatVeResponse.setNgayDat(donDatVe.getThoiGianDat());
            donDatVeResponse.setKieuThanhToan(donDatVe.getKieuThanhToan().toString());
            donDatVeResponse.setTrangThaiThanhToan(
                    donDatVe.getTrangThaiThanhToan() == 1 ? "PAID" : "UNPAID"
            );
            donDatVeResponse.setSoLuongVe(donDatVe.getSoLuongVe());

            donDatVeResponse.setDaDanhGia(danhGiaService.daDanhGia(donDatVe.getId()));

            int soVeDaHuy = veXeRepository.countCancelledTicketsByDonDatVeId(donDatVe.getId());
            if(soVeDaHuy > 0){
                donDatVeResponse.setTrangThai("Đã huỷ "+soVeDaHuy+"/"+donDatVe.getSoLuongVe());
            }
            else {
                donDatVeResponse.setTrangThai("Hoàn thành "+ 0+"/"+donDatVe.getSoLuongVe());
            }
            return donDatVeResponse;
        }).toList();
    }

    @Override
    public List<DonDatVeResponse> getMyBooking() {
        var context = SecurityContextHolder.getContext();
        String mail = context.getAuthentication().getName();
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(mail)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));

        List<DonDatVe> lstMyBooking = donDatVeRepository.findAllByNguoiDung_Email(mail);

        return lstMyBooking.stream().map(donDatVe -> {
            DonDatVeResponse donDatVeResponse = modelMapper.map(donDatVe, DonDatVeResponse.class);
            if(donDatVe.getNguoiDung() != null){
                donDatVeResponse.setTenNguoiDat(donDatVe.getNguoiDung().getHoTen());
            }
            donDatVeResponse.setTenHanhKhach(donDatVe.getTenHanhKhach());
            donDatVeResponse.setId(donDatVe.getId());
            donDatVeResponse.setNgayDat(donDatVe.getThoiGianDat());
            donDatVeResponse.setKieuThanhToan(donDatVe.getKieuThanhToan().toString());
            donDatVeResponse.setTrangThaiThanhToan(
                    donDatVe.getTrangThaiThanhToan() == 1 ? "PAID" : "UNPAID"
            );
            donDatVeResponse.setSoLuongVe(donDatVe.getSoLuongVe());

//            kiểm tra xem đã đánh giá chưa, dùng trong trang đơn hàng của tôi
            donDatVeResponse.setDaDanhGia(danhGiaService.daDanhGia(donDatVe.getId()));

            int soVeDaHuy = veXeRepository.countCancelledTicketsByDonDatVeId(donDatVe.getId());
            if(soVeDaHuy > 0){
                donDatVeResponse.setTrangThai("Đã huỷ "+soVeDaHuy+"/"+donDatVe.getSoLuongVe());
            }
            else {
                donDatVeResponse.setTrangThai("Hoàn thành "+ 0+"/"+donDatVe.getSoLuongVe());
            }
            return donDatVeResponse;
        }).toList();
    }

    @Scheduled(fixedRate = 30000)
    public void huyDonDatVeChuaThanhToan(){
        LocalDateTime now = LocalDateTime.now();
        List<DonDatVe> donCanHuy = donDatVeRepository.findByTrangThaiThanhToanAndKieuThanhToanAndThoiGianDatBefore(0,KieuThanhToan.VNPAY,now.minusMinutes(3));

        for(DonDatVe donDatVe : donCanHuy){
            veXeService.huyTatCaVeCuaDonDat(donDatVe.getId());
        }
    }

    @Override
    public void huyDon(Integer maDonDatVe){
        veXeService.huyTatCaVeCuaDonDat(maDonDatVe);
    }



}
