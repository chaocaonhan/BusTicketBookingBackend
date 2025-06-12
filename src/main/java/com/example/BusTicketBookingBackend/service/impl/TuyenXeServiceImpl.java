package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.response.TuyenXeDTO;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.DiemDonTra;
import com.example.BusTicketBookingBackend.models.TinhThanh;
import com.example.BusTicketBookingBackend.models.TuyenXe;
import com.example.BusTicketBookingBackend.repositories.ChuyenXeRepository;
import com.example.BusTicketBookingBackend.repositories.TinhThanhRepository;
import com.example.BusTicketBookingBackend.repositories.TuyenXeRepository;
import com.example.BusTicketBookingBackend.service.TuyenXeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TuyenXeServiceImpl implements TuyenXeService {
    private final TuyenXeRepository tuyenXeRepository;
    private final TinhThanhRepository tinhThanhRepository;
    private final ChuyenXeRepository chuyenXeRepository;

    @Override
    public TuyenXe timTuyenTheoTenHaiTinh(String tinhXuatPhat, String tinhDen) {
        return null;
    }

    @Override
    public List<TuyenXe> getAllTuyenXe() {
        return tuyenXeRepository.findAll();
    }

    @Override
    public String editTuyenXe(TuyenXeDTO tuyenXeDTO, Integer id) {
        Optional<TuyenXe> optionalTuyenXe = tuyenXeRepository.findById(id);
        if (!optionalTuyenXe.isPresent()) {
            return "Không tìm thấy tuyến xe với ID: " + id;
        }



        TuyenXe tuyenXe = optionalTuyenXe.get();

        tuyenXe.setTenTuyen(tuyenXeDTO.getTenTuyen());
        tuyenXe.setTinhDi(tinhThanhRepository.findByTen(tuyenXeDTO.getTinhDi()));
        tuyenXe.setTinhDen(tinhThanhRepository.findByTen(tuyenXeDTO.getTinhDen()));
        tuyenXe.setKhoangCach(tuyenXeDTO.getKhoangCach());

        tuyenXe.setThoiGianDiChuyen(tuyenXeDTO.getThoiGianDiChuyen());
        tuyenXeRepository.save(tuyenXe);

        return "Chỉnh sửa tuyến xe thành công!";
    }

    @Override
    public TuyenXe createTuyenXe(TuyenXeDTO tuyenXeDTO) {
        TinhThanh tinhDi = tinhThanhRepository.findByTen(tuyenXeDTO.getTinhDi());
        TinhThanh tinhDen = tinhThanhRepository.findByTen(tuyenXeDTO.getTinhDen());

        if (tinhDi == null || tinhDen == null) {
            throw new RuntimeException("Không tìm thấy tỉnh đi hoặc tỉnh đến!");
        }

        TuyenXe tuyenXe = new TuyenXe();
        tuyenXe.setTenTuyen(tuyenXeDTO.getTenTuyen());
        tuyenXe.setTinhDi(tinhDi);
        tuyenXe.setTinhDen(tinhDen);
        tuyenXe.setKhoangCach(tuyenXeDTO.getKhoangCach());
        tuyenXe.setThoiGianDiChuyen(tuyenXeDTO.getThoiGianDiChuyen());
        tuyenXe.setTrangThai(1);

        return tuyenXeRepository.save(tuyenXe);
    }

    @Override
    public String deleteTuyenXe(Integer id) {

        TuyenXe tx = tuyenXeRepository.findById(id).get();
        if (tx == null) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        tx.setTrangThai(0);
        tuyenXeRepository.save(tx);
        return "Đã xoá tuyến xe";
    }

    @Override
    public List<TuyenXe> getTop5TuyenXePhoBien() {
        List<Object[]> results = chuyenXeRepository.findTop5TuyenXePhoBien();

        return results.stream()
                .map(result -> (TuyenXe) result[0]) // Kết quả đầu tiên là đối tượng TuyenXe
                .collect(Collectors.toList());
    }




}
