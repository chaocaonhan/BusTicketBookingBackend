package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeDTO;
import com.example.BusTicketBookingBackend.dtos.response.ChuyenXeResponse;
import com.example.BusTicketBookingBackend.dtos.response.DiemDonCuaChuyen;
import com.example.BusTicketBookingBackend.enums.TrangThaiGhe;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.*;
import com.example.BusTicketBookingBackend.repositories.*;
import com.example.BusTicketBookingBackend.service.ChuyenXeService;
import com.example.BusTicketBookingBackend.service.VeXeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChuyenXeServiceImpl implements ChuyenXeService {
    ChuyenXeRepository chuyenXeRepository;
    TuyenXeRepository tuyenXeRepository;
    DiemDonTraRepository diemDonTraRepository;
    XeRepository xeRepository;
    TaiXeRepository taiXeRepository;
    VeXeService veXeService;
    ChoNgoiRepository choNgoiRepository;
    DatGheRepository datGheRepository;
    VeXeRepository vexeRepository;
    DiemDungTrenTuyenRepository diemDungTrenTuyenRepository;

//    public List<ChuyenXeResponse> timChuyenXeTheoTuyen(int idDiemDi, int idDiemDen, LocalDate ngayDi, LocalDate ngayVe, Boolean khuHoi) {
//        List<TuyenXe> tuyenDi = tuyenXeRepository.findTuyenXeByDiemDiAndDiemDen(idDiemDi, idDiemDen);
//        List<TuyenXe> tuyenVe = tuyenXeRepository.findTuyenXeByDiemDiAndDiemDen(idDiemDen, idDiemDi);
//
//        if (tuyenDi.isEmpty()) {
//            throw new RuntimeException("Không tìm thấy tuyến xe phù hợp.");
//        }
//
//        List<ChuyenXe> lstChuyenDi = chuyenXeRepository.findChuyenXeByTuyenXe(tuyenDi.get())
//                .stream()
//                .filter(chuyenXe -> {
//                    LocalDate ngayKhoiHanh = chuyenXe.getNgayKhoiHanh();
//                    LocalTime gioKhoiHanh = chuyenXe.getGioKhoiHanh();
//                    return ngayKhoiHanh.isEqual(ngayDi)
//                            && (ngayDi.isAfter(LocalDate.now()) || gioKhoiHanh.isAfter(LocalTime.now()));
//                })
//                .toList();
//
//        List<ChuyenXe> lstChuyenVe = new ArrayList<>();
//        if (Boolean.TRUE.equals(khuHoi)) {
//            if (tuyenVe.isEmpty()) {
//                throw new RuntimeException("Không tìm thấy chuyến xe chiều về");
//            }
//            lstChuyenVe = chuyenXeRepository.findChuyenXeByTuyenXe(tuyenVe.get())
//                    .stream()
//                    .filter(chuyenXe -> {
//                        LocalDate ngayKhoiHanh = chuyenXe.getNgayKhoiHanh();
//                        LocalTime gioKhoiHanh = chuyenXe.getGioKhoiHanh();
//                        return ngayKhoiHanh.isEqual(ngayVe)
//                                && (ngayVe.isAfter(LocalDate.now()) || gioKhoiHanh.isAfter(LocalTime.now()));
//                    })
//                    .toList();
//        }
//
//        if (lstChuyenDi.isEmpty() && lstChuyenVe.isEmpty()) {
//            throw new AppException(ErrorCode.DATA_NOT_FOUND);
//        }
//
//        // Gộp 2 list chuyến đi và về
//        List<ChuyenXe> allChuyenXe = new ArrayList<>();
//        allChuyenXe.addAll(lstChuyenDi);
//        allChuyenXe.addAll(lstChuyenVe);
//
//        return allChuyenXe.stream()
//                .map(this::convertToResponse)
//                .toList();
//    }

    @Override
    public List<ChuyenXeResponse> timChuyenXeTheoTuyen(int idDiemDi, int idDiemDen, LocalDate ngayDi, LocalDate ngayVe, Boolean khuHoi) {
        List<TuyenXe> tuyenDi = tuyenXeRepository.findTuyenXeByDiemDiAndDiemDen(idDiemDi, idDiemDen);
        List<TuyenXe> tuyenVe = tuyenXeRepository.findTuyenXeByDiemDiAndDiemDen(idDiemDen, idDiemDi);

        if (tuyenDi.isEmpty()) {
            throw new RuntimeException("Không tìm thấy tuyến xe phù hợp.");
        }

        // Lấy chuyến xe từ TẤT CẢ các tuyến đi (thay vì chỉ tuyến đầu tiên)
        List<ChuyenXe> lstChuyenDi = tuyenDi.stream()
                .flatMap(tuyen -> chuyenXeRepository.findChuyenXeByTuyenXe(tuyen).stream())
                .filter(chuyenXe -> {
                    LocalDate ngayKhoiHanh = chuyenXe.getNgayKhoiHanh();
                    LocalTime gioKhoiHanh = chuyenXe.getGioKhoiHanh();
                    return ngayKhoiHanh.isEqual(ngayDi)
                            && (ngayDi.isAfter(LocalDate.now()) || gioKhoiHanh.isAfter(LocalTime.now()));
                })
                .toList();

        List<ChuyenXe> lstChuyenVe = new ArrayList<>();
        if (Boolean.TRUE.equals(khuHoi)) {
            if (tuyenVe.isEmpty()) {
                throw new RuntimeException("Không tìm thấy chuyến xe chiều về");
            }

            // Lấy chuyến xe từ TẤT CẢ các tuyến về (thay vì chỉ tuyến đầu tiên)
            lstChuyenVe = tuyenVe.stream()
                    .flatMap(tuyen -> chuyenXeRepository.findChuyenXeByTuyenXe(tuyen).stream())
                    .filter(chuyenXe -> {
                        LocalDate ngayKhoiHanh = chuyenXe.getNgayKhoiHanh();
                        LocalTime gioKhoiHanh = chuyenXe.getGioKhoiHanh();
                        return ngayKhoiHanh.isEqual(ngayVe)
                                && (ngayVe.isAfter(LocalDate.now()) || gioKhoiHanh.isAfter(LocalTime.now()));
                    })
                    .toList();
        }

        if (lstChuyenDi.isEmpty() && lstChuyenVe.isEmpty()) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }

        // Gộp 2 list chuyến đi và về
        List<ChuyenXe> allChuyenXe = new ArrayList<>();
        allChuyenXe.addAll(lstChuyenDi);
        allChuyenXe.addAll(lstChuyenVe);

        return allChuyenXe.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public void upDateSoGheTrong(int maChuyenXe){
        ChuyenXe chuyenXe = chuyenXeRepository.findById(maChuyenXe).get();
        if (chuyenXe == null) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        chuyenXe.setSoGheTrong(datGheRepository.countSeatAvailableByChuyenXe_Id(maChuyenXe));
        chuyenXeRepository.save(chuyenXe);
    }




    @Override
    public List<ChuyenXeResponse> getAll() {
        List<ChuyenXe> lsChuyenXe = chuyenXeRepository.findAll();
        for(ChuyenXe chuyenXe : lsChuyenXe){
            chuyenXe.setSoGheTrong(datGheRepository.countSeatAvailableByChuyenXe_Id(chuyenXe.getId()));
            chuyenXeRepository.save(chuyenXe);
        }
        return lsChuyenXe.stream().map(this::convertToResponse).toList();
    }

    @Override
    public ChuyenXeResponse editChuyenXe(ChuyenXeDTO chuyenXeDto, Integer idChuyenXe) {
        ChuyenXe chuyenXe = chuyenXeRepository.findById(idChuyenXe).get();
        if (chuyenXe == null) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }

        chuyenXe.setTuyenXe(tuyenXeRepository.findByTenTuyen(chuyenXeDto.getTenTuyen()));
        chuyenXe.setXe(xeRepository.findByBienSo(chuyenXeDto.getBienSoXe()));
        chuyenXe.setTaiXe(taiXeRepository.findByHoTen(chuyenXeDto.getTaiXe()));
        chuyenXe.setDiemDi(diemDonTraRepository.findDiemDonTraByTenDiemDon(chuyenXeDto.getDiemDi()));
        chuyenXe.setDiemDen(diemDonTraRepository.findDiemDonTraByTenDiemDon(chuyenXeDto.getDiemDen()));
        chuyenXe.setNgayKhoiHanh(chuyenXeDto.getNgayKhoiHanh());
        chuyenXe.setGioKhoiHanh(chuyenXeDto.getGioKhoiHanh());
        chuyenXe.setGioKetThuc(chuyenXeDto.getGioKetThuc());
        chuyenXe.setGiaVe(chuyenXeDto.getGiaVe());

        return convertToResponse(chuyenXeRepository.save(chuyenXe));



    }

    private ChuyenXeResponse convertToResponse(ChuyenXe chuyenXe) {
        return ChuyenXeResponse.builder()
                .id(chuyenXe.getId())
                .tenTuyen(chuyenXe.getTuyenXe().getTenTuyen())
                .diemDi(chuyenXe.getDiemDi().getTenDiemDon())
                .diemDen(chuyenXe.getDiemDen().getTenDiemDon())
                .ngayKhoiHanh(chuyenXe.getNgayKhoiHanh())
                .gioKhoiHanh(chuyenXe.getGioKhoiHanh())
                .gioKetThuc(chuyenXe.getGioKetThuc())
                .bienSo(chuyenXe.getXe().getBienSo())
                .taiXe(chuyenXe.getTaiXe().getHoTen())
                .giaVe(chuyenXe.getGiaVe())
                .tenLoaiXe(chuyenXe.getXe().getLoaiXe().getTenLoaiXe())
                .soGheTrong(datGheRepository.countSeatAvailableByChuyenXe_Id(chuyenXe.getId()))

                .build();
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

        List<ChoiNgoi> choiNgois = choNgoiRepository.findByLoaiXe_Id(loaiXe.getId());

        chuyenXeRepository.save(cx);

        for (ChoiNgoi cn : choiNgois) {
            DatGhe datGhes = new DatGhe();
            datGhes.setChuyenXe(cx);
            datGhes.setChoNgoi(cn);
            datGhes.setTrangThai(TrangThaiGhe.AVAILABLE);
            datGheRepository.save(datGhes);
        }

        return "Thêm chuyến xe thành công";
    }

    @Override
    public void capNhatSoGheTrong(Integer idChuyenXe){
        ChuyenXe chuyenXe = chuyenXeRepository.findById(idChuyenXe).get();
        if (chuyenXe == null) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }

        chuyenXe.setSoGheTrong(datGheRepository.countSeatAvailableByChuyenXe_Id(idChuyenXe));

    }

    @Override
    public List<DiemDonCuaChuyen> getLichTrinhChuyenXe(Integer idChuyenXe) {
        ChuyenXe chuyenXe = chuyenXeRepository.findById(idChuyenXe).get();
        if (chuyenXe == null) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        TuyenXe tuyenXe = chuyenXe.getTuyenXe();
        List<DiemDungTrenTuyen> lstDiemDungCuaTuyen = diemDungTrenTuyenRepository.findByTuyenId(tuyenXe.getId());
        LocalTime gioXeXuatPhat = chuyenXe.getGioKhoiHanh();
        List<DiemDonCuaChuyen> lichTrinh = new ArrayList<>();

        for( DiemDungTrenTuyen diemDung : lstDiemDungCuaTuyen ){
            DiemDonCuaChuyen diemDonCuaChuyen = new DiemDonCuaChuyen();

            diemDonCuaChuyen.setTenDiemDon(diemDung.getDiemDonTra().getTenDiemDon());
            diemDonCuaChuyen.setThuTu(diemDung.getThuTuDiemDung());

            int soPhutDeDiTuDiemDauDenDiemHienTai = diemDung.getThoiGianTuDiemDau();
            int soGio = soPhutDeDiTuDiemDauDenDiemHienTai / 60;
            int soPhut = soPhutDeDiTuDiemDauDenDiemHienTai % 60;

            diemDonCuaChuyen.setThoiGianXeDen(chuyenXe.getGioKhoiHanh().plusHours(soGio).plusMinutes(soPhut));
            diemDonCuaChuyen.setKhoangCahDenDiemDau(diemDung.getKhoangCachToiDiemDau());

            lichTrinh.add(diemDonCuaChuyen);


        }
        return lichTrinh;

    }


    //dùng để tìm chuyến xe trong đánh giá
    @Override
    public int getChuyenXeIdFromDonDatVeId(int donDatVeId) {
        // Step 1: Find the associated Vexe by DonDatVe ID
        List<Vexe> veXes = vexeRepository.findByDonDatVe_Id(donDatVeId);
        Vexe vexe = veXes.get(0);

        // Step 2: Access the linked DatGhe and ChuyenXe
        if (vexe.getDatGhe() == null || vexe.getDatGhe().getChuyenXe() == null) {
            throw new EntityNotFoundException("Không tìm thấy chuyến xe cho vé xe: " + vexe.getId());
        }

        // Step 3: Return ChuyenXe ID
        return vexe.getDatGhe().getChuyenXe().getId();
    }

    @Override
    public int huyChuyen(int idChuyenXe) {
        ChuyenXe chuyenXe = chuyenXeRepository.findById(idChuyenXe)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        chuyenXe.setTrangThai(ChuyenXe.TrangThai.CANCELED);
        chuyenXeRepository.save(chuyenXe);
        veXeService.huyVeTheoChuyenXe(idChuyenXe);
        return 1;
    }


}
