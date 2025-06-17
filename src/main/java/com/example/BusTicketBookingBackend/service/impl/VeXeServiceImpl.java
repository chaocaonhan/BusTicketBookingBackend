package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeVaGheCanDat;
import com.example.BusTicketBookingBackend.dtos.request.DatVeRequest;
import com.example.BusTicketBookingBackend.dtos.response.VeXeResponse;
import com.example.BusTicketBookingBackend.enums.TrangThaiDonDat;
import com.example.BusTicketBookingBackend.enums.TrangThaiGhe;
import com.example.BusTicketBookingBackend.enums.TrangThaiVe;
import com.example.BusTicketBookingBackend.models.DatGhe;
import com.example.BusTicketBookingBackend.models.DonDatVe;
import com.example.BusTicketBookingBackend.models.Vexe;
import com.example.BusTicketBookingBackend.repositories.DatGheRepository;
import com.example.BusTicketBookingBackend.repositories.DonDatVeRepository;
import com.example.BusTicketBookingBackend.repositories.VeXeRepository;
import com.example.BusTicketBookingBackend.service.VeXeService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VeXeServiceImpl implements VeXeService {
    VeXeRepository veXeRepository;
    DonDatVeRepository donDatVeRepository;
    DatGheRepository datGheRepository;
    private final EmailServiceImpl emailServiceImpl;

    @Override
    public String taoVeXeChoChuyenXe(DatVeRequest datVeRequest, Integer maDonDat) {
        ChuyenXeVaGheCanDat chuyenDi = datVeRequest.getChuyenDi();
        if (chuyenDi != null && chuyenDi.getDanhSachGheMuonDat() != null && !chuyenDi.getDanhSachGheMuonDat().isEmpty()) {
            chuyenDi.getDanhSachGheMuonDat().forEach(maGhe -> {
                Vexe veXe = new Vexe();
                veXe.setDonDatVe(donDatVeRepository.findById(maDonDat).orElseThrow(() ->
                        new IllegalArgumentException("Mã đơn đặt vé không hợp lệ: " + maDonDat)
                ));
                veXe.setDatGhe(datGheRepository.findById(maGhe).orElseThrow(() ->
                        new IllegalArgumentException("Không tìm thấy ghế với mã: " + maGhe)
                ));
                veXe.setDiemDon(chuyenDi.getDiemDon());
                veXe.setDiemTra(chuyenDi.getDiemTra());
                veXe.setTrangThaiVe(TrangThaiVe.BOOKED);
                veXeRepository.save(veXe);
            });
        }

        // Kiểm tra và xử lý thông tin "chuyenVe"
        ChuyenXeVaGheCanDat chuyenVe = datVeRequest.getChuyenVe();
        if (chuyenVe != null && chuyenVe.getDanhSachGheMuonDat() != null && !chuyenVe.getDanhSachGheMuonDat().isEmpty()) {
            chuyenVe.getDanhSachGheMuonDat().forEach(maGhe -> {
                Vexe veXe = new Vexe();
                veXe.setDonDatVe(donDatVeRepository.findById(maDonDat).orElseThrow(() ->
                        new IllegalArgumentException("Mã đơn đặt vé không hợp lệ: " + maDonDat)
                ));
                veXe.setDatGhe(datGheRepository.findById(maGhe).orElseThrow(() ->
                        new IllegalArgumentException("Không tìm thấy ghế với mã: " + maGhe)
                ));
                veXe.setDiemDon(chuyenDi.getDiemDon());
                veXe.setDiemTra(chuyenDi.getDiemTra());
                veXe.setTrangThaiVe(TrangThaiVe.BOOKED);
                veXeRepository.save(veXe);
            });
        }

        return "Tạo vé thành công!";
    }

    @Override
    public List<VeXeResponse> layDanhSachVeXeTheoMaDonDat(Integer maDonDat) {
        List<Vexe> veXes = veXeRepository.findAllByDonDatVe_Id(maDonDat);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<VeXeResponse> veXeResponses = veXes.stream().map(vexe -> {
            VeXeResponse response = new VeXeResponse();
            response.setMaVeXe(vexe.getId());
            response.setTenTuyen(vexe.getDatGhe().getChuyenXe().getTuyenXe().getTenTuyen() +" "+ vexe.getDatGhe().getChuyenXe().getNgayKhoiHanh().format(formatter));
            response.setTrangThaiVe(vexe.getTrangThaiVe());
            response.setGiaVe(vexe.getDatGhe().getChuyenXe().getGiaVe());
            response.setLoaiXe(vexe.getDatGhe().getChuyenXe().getXe().getLoaiXe().getTenLoaiXe());
            response.setTenGhe(vexe.getDatGhe().getChoNgoi().getTenghe());
            response.setBienSoXe(vexe.getDatGhe().getChuyenXe().getXe().getBienSo());
            response.setTrungChuyenTu("Tự di chuyển");
            response.setDiemBatDau(vexe.getDiemDon());
            response.setThoiGianBatDau(vexe.getDatGhe().getChuyenXe().getGioKhoiHanh());
            response.setThoiGianKetThuc(vexe.getDatGhe().getChuyenXe().getGioKetThuc());
            response.setDiemKetThuc(vexe.getDiemTra());
            response.setTrungChuyenDen("Tự di chuyển");
            return response;
        }).toList();
        return veXeResponses;
    }

    @Override
    public void huyTatCaVeCuaDonDat(Integer maDonDat) {
        List<Vexe> veXes = veXeRepository.findAllByDonDatVeWithIDMaDonDat
                (maDonDat);
        for(Vexe veXe : veXes){
            DatGhe datGheCanSua = veXe.getDatGhe();
            if (datGheCanSua != null) {
                datGheCanSua.setTrangThai(TrangThaiGhe.AVAILABLE);
                datGheRepository.save(datGheCanSua);
            }

            veXe.setTrangThaiVe(TrangThaiVe.CANCELED);
            veXeRepository.save(veXe);
        }
    }



    @Override
    public TrangThaiDonDat kiemTraTrangDonDat(Integer maDonDat){
        List<Vexe> veXes = veXeRepository.findAllByDonDatVeWithIDMaDonDat
                (maDonDat);

        String trangThai = "";
        int tongSoLuongVe = veXes.size();
        int soVeDaHuy=0;
        int soVeHoanThanh=0;
        int soVeDangDat=0;
        for(Vexe veXe : veXes){
            if(veXe.getTrangThaiVe().equals(TrangThaiVe.CANCELED)){
                soVeDaHuy++;
            }
            if (veXe.getTrangThaiVe().equals(TrangThaiVe.BOOKED)){
                soVeDangDat++;
            }
            if (veXe.getTrangThaiVe().equals(TrangThaiVe.COMPLETED)){
                soVeHoanThanh++;
            }
        }

        if(soVeDaHuy==tongSoLuongVe){
            return TrangThaiDonDat.CANCELED;
        }
        if(soVeHoanThanh+soVeDaHuy==tongSoLuongVe){
            return TrangThaiDonDat.COMPLETED;
        }
        else {
            return TrangThaiDonDat.BOOKED;
        }
    }



    @Override
    public Integer huyVeXe(Integer maVeXe) {
        Optional<Vexe> vexe = veXeRepository.findById(maVeXe);

        if(vexe != null && vexe.isPresent()){

            Vexe veCanHuy = vexe.get();

//          thay doi tranvg thais ve
            veCanHuy.setTrangThaiVe(TrangThaiVe.CANCELED);
            veXeRepository.save(veCanHuy);

            // sửa trang thái ghế trên chuyến
            DatGhe datGheCanSua = veCanHuy.getDatGhe();
            datGheCanSua.setTrangThai(TrangThaiGhe.AVAILABLE);
            datGheRepository.save(datGheCanSua);

            //sửa tổng tiền hoá đơn
            DonDatVe donDatVe = veCanHuy.getDonDatVe();
            donDatVe.setTongTien(donDatVe.getTongTien()-veCanHuy.getDatGhe().getChuyenXe().getGiaVe());
            donDatVeRepository.save(donDatVe);

            return 1;
        }
        return 0;
    }

    @Override
    public void huyVeTheoChuyenXe(Integer chuyenXeId) throws MessagingException {
        List<DonDatVe> donDatVeCanHuy = donDatVeRepository.findByChuyenXeId(chuyenXeId);
        for (DonDatVe donDatVe : donDatVeCanHuy) {
            emailServiceImpl.sendSimpleCancelBookingEmail(donDatVe.getId());
            huyTatCaVeCuaDonDat(donDatVe.getId());
            donDatVe.setTrangThaiDonDat(TrangThaiDonDat.CANCELED);
        }
    }

    @Override
    public void capNhatTrangThaiVeKhiHoanThanhChuyen(Integer chuyenXeId){
//        lây tất cả vé trên chuyến
        List<Vexe> veXes = veXeRepository.findAllByChuyenXeId(chuyenXeId);
        for(Vexe veXe : veXes){
            if(veXe.getTrangThaiVe().equals(TrangThaiVe.BOOKED)){
                veXe.setTrangThaiVe(TrangThaiVe.COMPLETED);
                veXeRepository.save(veXe);
            }
        }
    }





}

