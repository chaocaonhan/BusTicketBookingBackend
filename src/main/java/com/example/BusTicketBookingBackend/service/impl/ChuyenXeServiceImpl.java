package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeDTO;
import com.example.BusTicketBookingBackend.dtos.response.ChuyenXeResponse;
import com.example.BusTicketBookingBackend.dtos.response.DiemDonCuaChuyen;
import com.example.BusTicketBookingBackend.dtos.response.DonDatVeResponse;
import com.example.BusTicketBookingBackend.enums.TrangThaiDonDat;
import com.example.BusTicketBookingBackend.enums.TrangThaiGhe;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.*;
import com.example.BusTicketBookingBackend.repositories.*;
import com.example.BusTicketBookingBackend.service.ChuyenXeService;
import com.example.BusTicketBookingBackend.service.DiemDungTrenTuyenService;
import com.example.BusTicketBookingBackend.service.VeXeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final DiemDungTrenTuyenService diemDungTrenTuyenService;

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
                            && chuyenXe.getTrangThai() == ChuyenXe.TrangThai.SCHEDULED
                            && (ngayDi.isAfter(LocalDate.now()) || gioKhoiHanh.isAfter(LocalTime.now()));
                })
                .toList();

        List<ChuyenXe> lstChuyenVe = new ArrayList<>();
        if (Boolean.TRUE.equals(khuHoi)) {
            if (tuyenVe.isEmpty()) {
                throw new RuntimeException("Không tìm thấy chuyến xe chiều về");
            }

            lstChuyenVe = tuyenVe.stream()
                    .flatMap(tuyen -> chuyenXeRepository.findChuyenXeByTuyenXe(tuyen).stream())
                    .filter(chuyenXe -> {
                        LocalDate ngayKhoiHanh = chuyenXe.getNgayKhoiHanh();
                        LocalTime gioKhoiHanh = chuyenXe.getGioKhoiHanh();
                        return ngayKhoiHanh.isEqual(ngayVe)
                                && chuyenXe.getTrangThai() == ChuyenXe.TrangThai.SCHEDULED
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
                .trangThai(chuyenXe.getTrangThai().toString())
                .giaVe(chuyenXe.getGiaVe())
                .tenLoaiXe(chuyenXe.getXe().getLoaiXe().getTenLoaiXe())
                .soGheTrong(datGheRepository.countSeatAvailableByChuyenXe_Id(chuyenXe.getId()))

                .build();
    }


    @Override
    public String taoChuyenXe(ChuyenXeDTO chuyenXe) {
        ChuyenXe cx = new ChuyenXe();
        TuyenXe tuyenXe = tuyenXeRepository.findById(Integer.valueOf(chuyenXe.getTenTuyen())).get();

        cx.setTuyenXe(tuyenXe);
        cx.setXe(xeRepository.findByBienSo(chuyenXe.getBienSoXe()));
        cx.setTaiXe(taiXeRepository.findByHoTen(chuyenXe.getTaiXe()));


        cx.setDiemDi(diemDungTrenTuyenService.getDiemDau(tuyenXe.getId()));
        cx.setDiemDen(diemDungTrenTuyenService.getDiemCuoi(tuyenXe.getId()));

        LoaiXe loaiXe = xeRepository.findByBienSo(chuyenXe.getBienSoXe()).getLoaiXe();
        int soGhe = loaiXe.getSoLuongGhe();
        cx.setSoGheTrong(soGhe);
        cx.setTrangThai(ChuyenXe.TrangThai.SCHEDULED);



        cx.setNgayKhoiHanh(chuyenXe.getNgayKhoiHanh());
        cx.setGioKhoiHanh(chuyenXe.getGioKhoiHanh());
        cx.setGioKetThuc(chuyenXe.getGioKetThuc());
        cx.setGiaVe(chuyenXe.getGiaVe());


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

        List<Vexe> veXes = vexeRepository.findByDonDatVe_Id(donDatVeId);
        Vexe vexe = veXes.get(0);

        // Step 2: Access the linked DatGhe and ChuyenXe
        if (vexe.getDatGhe() == null || vexe.getDatGhe().getChuyenXe() == null) {
            throw new EntityNotFoundException("Không tìm thấy chuyến xe cho vé xe: " + vexe.getId());
        }


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

    @Override
    public Page<ChuyenXeResponse> getAllChuyenXeByTrangThai(ChuyenXe.TrangThai trangThaiChuyenXe, Pageable pageable) {
        return chuyenXeRepository.findAllByTrangThai(trangThaiChuyenXe,pageable).map(this::convertToResponse);
    }

    @Override
    public Page<ChuyenXeResponse> searchChuyenXe(LocalDate keyword, Pageable pageable, ChuyenXe.TrangThai trangThaiChuyenXe) {
        return chuyenXeRepository.findByNgayKhoiHanhAndTrangThai(keyword,trangThaiChuyenXe,pageable).map(this::convertToResponse);
    }

    @Scheduled(initialDelay = 10000, fixedRate = 1800000)
    public void capNhatTrangThaiChuyenXe() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();


        List<ChuyenXe> danhSachChuyenXe = chuyenXeRepository.findAll();
        for (ChuyenXe chuyenXe : danhSachChuyenXe) {
            LocalDate ngayKhoiHanh = chuyenXe.getNgayKhoiHanh();
            LocalTime gioKhoiHanh = chuyenXe.getGioKhoiHanh();
            LocalTime gioKetThuc = chuyenXe.getGioKetThuc();

            ChuyenXe.TrangThai trangThaiHienTai = chuyenXe.getTrangThai();

            if(chuyenXe.getTrangThai() != ChuyenXe.TrangThai.CANCELED){
                if (ngayKhoiHanh.isEqual(today)) {
                    if (currentTime.isAfter(gioKetThuc)) {
                        if (trangThaiHienTai != ChuyenXe.TrangThai.COMPLETED) {
                            chuyenXe.setTrangThai(ChuyenXe.TrangThai.COMPLETED);
                        }
                    } else if (currentTime.isAfter(gioKhoiHanh)) {
                        if (trangThaiHienTai == ChuyenXe.TrangThai.SCHEDULED) {
                            chuyenXe.setTrangThai(ChuyenXe.TrangThai.DEPARTED);
                        }
                    }
                } else if (ngayKhoiHanh.isBefore(today)) {
                    // Ngày khởi hành đã qua → tự động chuyển sang COMPLETED nếu chưa cập nhật
                    if (trangThaiHienTai != ChuyenXe.TrangThai.COMPLETED) {
                        chuyenXe.setTrangThai(ChuyenXe.TrangThai.COMPLETED);
                    }
                }
            }

            chuyenXeRepository.save(chuyenXe);
        }
    }




}
