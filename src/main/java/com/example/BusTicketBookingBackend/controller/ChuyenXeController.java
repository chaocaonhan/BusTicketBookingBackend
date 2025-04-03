package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.response.ChuyenXeDTO;
import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeSearchDTO;
import com.example.BusTicketBookingBackend.models.ChuyenXe;
import com.example.BusTicketBookingBackend.service.ChuyenXeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/chuyenxe")
@RequiredArgsConstructor
public class ChuyenXeController {

    private final ChuyenXeService chuyenXeService;

    @GetMapping("/search")
    public ResponseEntity<?> getChuyenXe(
            @RequestParam("tinhDi") String tinhDi,
            @RequestParam("tinhDen") String tinhDen,
            @RequestParam("ngayDi")
            @DateTimeFormat(pattern = "d/M/yyyy") LocalDate ngayDi) {

        try {
            List<ChuyenXe> danhSachChuyenXe = chuyenXeService.timChuyenXeTheoTuyen(tinhDi, tinhDen, ngayDi);

            List<ChuyenXeSearchDTO> dtoList = danhSachChuyenXe.stream().map(chuyenXe ->
                    ChuyenXeSearchDTO.builder()// Giả sử class Xe có trường LoaiXe
                            .diemDi(chuyenXe.getDiemDi().getTenDiemDon()) // Giả sử class DiemDonTra có trường tenDiem
                            .diemDen(chuyenXe.getDiemDen().getTenDiemDon())
                            .gioKhoiHanh(chuyenXe.getGioKhoiHanh())
                            .gioKetThuc(chuyenXe.getGioKetThuc())
                            .giaVe(chuyenXe.getGiaVe())
                            .tenLoaiXe(chuyenXe.getXe().getLoaiXe().getTenLoaiXe()) // Giả sử class Xe có trường tenLoaiXe
                            .soGheTrong(chuyenXe.getSoGheTrong())
                            .build()
            ).toList();

            return ResponseEntity.ok(dtoList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/TaoChuyenXe")
    public String createChuyenXe(@RequestBody ChuyenXeDTO chuyenXedto) {
        return chuyenXeService.taoChuyenXe(chuyenXedto);
    }
}
