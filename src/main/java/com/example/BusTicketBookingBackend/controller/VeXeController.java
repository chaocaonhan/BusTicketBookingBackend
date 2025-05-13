package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.service.VeXeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ve-xe")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VeXeController {
    VeXeService veXeService;

    @GetMapping("/maDonDatVe")
    public ApiResponse getDanhSachVeTheoMaDonDat(@RequestParam Integer maDonDatVe) {
        return ApiResponse.builder()
                .code(200)
                .message("Danh sách vé của hoá đơn"+maDonDatVe)
                .result(veXeService.layDanhSachVeXeTheoMaDonDat(maDonDatVe))
                .build();
    }
}
