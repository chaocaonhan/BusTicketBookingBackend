package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.request.DanhGiaRequest;
import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.dtos.response.DanhGiaResponse;
import com.example.BusTicketBookingBackend.service.DanhGiaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/danhGia")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DanhGiaController {
    DanhGiaService danhGiaService;

    @PostMapping("")
    public ApiResponse themDanhGia(@RequestBody DanhGiaRequest request) {
        return ApiResponse.builder()
                .code(200)
                .message("Đã gửi đánh giá")
                .result(danhGiaService.themDanhGia(request))
                .build();
    }

    @GetMapping
    public ApiResponse getAllDanhGia(){
        return ApiResponse.builder()
                .code(200)
                .message("Danh sách đánh giá")
                .result(danhGiaService.getAllDanhGia())
                .build();
    }

    @GetMapping("/getByIdDonDat/{id}")
    public ApiResponse getDanhGiaByIdDonDat(@PathVariable Integer id){
        return ApiResponse.builder()
                .code(200)
                .message("Đánh giá của đơn đặt "+ id)
                .result(danhGiaService.getDanhGiaByMaDonDatVe(id))
                .build();
    }
}
