package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.DiemDungTrenTuyenDTO;
import com.example.BusTicketBookingBackend.service.DiemDungTrenTuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diem-dung")
@RequiredArgsConstructor
public class DiemDungTrenTuyenController {

    private final DiemDungTrenTuyenService diemDungTrenTuyenService;

    @GetMapping("/tuyen/{idTuyen}")
    public ResponseEntity<List<DiemDungTrenTuyenDTO>> getDiemDungByTuyen(@PathVariable Integer idTuyen) {
        List<DiemDungTrenTuyenDTO> danhSachDiemDung = diemDungTrenTuyenService.danhSachDiemDungCuaMotTuyen(idTuyen);
        return ResponseEntity.ok(danhSachDiemDung);
    }
}
