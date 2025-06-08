package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeVaGheCanDat;
import com.example.BusTicketBookingBackend.dtos.request.DatVeRequest;
import com.example.BusTicketBookingBackend.dtos.request.FindingRequest;
import com.example.BusTicketBookingBackend.dtos.response.DonDatVeResponse;
import com.example.BusTicketBookingBackend.enums.KieuThanhToan;
import com.example.BusTicketBookingBackend.enums.TrangThaiDonDat;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        donDatVe.setTrangThaiDonDat(TrangThaiDonDat.BOOKED);

        ChuyenXeVaGheCanDat chuyenDi = datVeRequest.getChuyenDi();
        ChuyenXeVaGheCanDat chuyenVe = datVeRequest.getChuyenVe();

        boolean datVeThanhCong = false;

        if (chuyenDi != null && datGheService.capNhatTrangThaiGhe(chuyenDi)) {
            chuyenXeService.capNhatSoGheTrong(chuyenDi.getIdChuyenXe());
            if (datVeRequest.getLoaiChuyenDi() == 1 && chuyenVe != null) {
                if (datGheService.capNhatTrangThaiGhe(chuyenVe)) {
                    chuyenXeService.capNhatSoGheTrong(chuyenVe.getIdChuyenXe());
                    datVeThanhCong = true;
                }
            } else {
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
        return donDatVeRepository.findAll().stream()
                .map(this::toDonDatVeResponse)
                .toList();
    }

    @Override
    public List<DonDatVeResponse> getMyBooking() {
        var context = SecurityContextHolder.getContext();
        String mail = context.getAuthentication().getName();
        nguoiDungRepository.findByEmail(mail)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return donDatVeRepository.findAllByNguoiDung_Email(mail).stream()
                .map(this::toDonDatVeResponse)
                .toList();
    }

    @Override
    public List<DonDatVeResponse> getDonDatVeByIdChuyenXe(Integer id) {
        return donDatVeRepository.findByChuyenXeId(id).stream().map(this::toDonDatVeResponse).toList();
    }

    @Override
    public Page<DonDatVeResponse> getAllDonDatVeByTrangThai(TrangThaiDonDat trangThai, Pageable pageable) {
        return donDatVeRepository
                .findAllByTrangThaiDonDat(trangThai, pageable)
                .map(this::toDonDatVeResponse);
    }


    @Override
    public Page<DonDatVeResponse> searchDonDatVe(String keyword, Pageable pageable,TrangThaiDonDat trangThaiDonDat) {
        return donDatVeRepository.findByKeywordAndTrangThai(keyword,trangThaiDonDat, pageable)
                .map(this::toDonDatVeResponse);
    }

    @Override
    public Optional<DonDatVeResponse> traCuuDonDat(FindingRequest findingRequest){
        DonDatVe donDatVe = donDatVeRepository.findById(Integer.parseInt(findingRequest.getMaDonDatVe())).get();
        if(donDatVe == null){
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        if(!donDatVe.getSDT().equals(findingRequest.getSdt())){
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        return donDatVeRepository.findById(Integer.parseInt(findingRequest.getMaDonDatVe()))
                .map(this::toDonDatVeResponse);
    }

    @Override
    public void huyDon(Integer maDonDatVe){
        veXeService.huyTatCaVeCuaDonDat(maDonDatVe);
        DonDatVe donDatVe = donDatVeRepository.findById(maDonDatVe).get();
        donDatVe.setTrangThaiDonDat(TrangThaiDonDat.CANCELED);
        donDatVeRepository.save(donDatVe);
    }

    @Scheduled(fixedRate = 30000)
    public void huyDonDatVeChuaThanhToan(){
        LocalDateTime now = LocalDateTime.now();
        List<DonDatVe> donCanHuy = donDatVeRepository.findByTrangThaiThanhToanAndKieuThanhToanAndThoiGianDatBefore(0,KieuThanhToan.VNPAY,now.minusMinutes(3));
        for(DonDatVe donDatVe : donCanHuy){
            veXeService.huyTatCaVeCuaDonDat(donDatVe.getId());
            donDatVe.setTrangThaiDonDat(TrangThaiDonDat.CANCELED);
            donDatVeRepository.save(donDatVe);
        }
    }

    @Scheduled(fixedRate = 180000)
    public void capNhatTrangThaiDonDat(){
        List<DonDatVe> lstDonDat = donDatVeRepository.findAll();
        for(DonDatVe donDatVe : lstDonDat){
            donDatVe.setTrangThaiDonDat(veXeService.kiemTraTrangDonDat(donDatVe.getId()));
            donDatVeRepository.save(donDatVe);
        }
    }

    private DonDatVeResponse toDonDatVeResponse(DonDatVe donDatVe) {
        DonDatVeResponse response = modelMapper.map(donDatVe, DonDatVeResponse.class);
        if (donDatVe.getNguoiDung() != null) {
            response.setTenNguoiDat(donDatVe.getNguoiDung().getHoTen());
        }
        response.setTenHanhKhach(donDatVe.getTenHanhKhach());
        response.setId(donDatVe.getId());
        response.setNgayDat(donDatVe.getThoiGianDat());
        response.setKieuThanhToan(donDatVe.getKieuThanhToan().toString());
        response.setTrangThaiThanhToan(donDatVe.getTrangThaiThanhToan() == 1 ? "PAID" : "UNPAID");
        response.setSoLuongVe(donDatVe.getSoLuongVe());
        response.setDaDanhGia(danhGiaService.daDanhGia(donDatVe.getId()));
        int soVeDaHuy = veXeRepository.countCancelledTicketsByDonDatVeId(donDatVe.getId());
        int soVeDaHoanThanh = veXeRepository.countCompleteTicketsByDonDatVeId(donDatVe.getId());
        if(soVeDaHoanThanh > 0){
            response.setTrangThai("Hoàn thành " + soVeDaHoanThanh +"/"+donDatVe.getSoLuongVe());
        } else if(soVeDaHuy > 0) {
            response.setTrangThai("Đã huỷ " + soVeDaHuy + "/" + donDatVe.getSoLuongVe());
        } else {
            response.setTrangThai("Hoàn thành 0/" + donDatVe.getSoLuongVe());
        }
        return response;
    }
}
