package com.example.BusTicketBookingBackend.controller;


import com.example.BusTicketBookingBackend.dtos.request.DiemDonTraRequest;
import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.models.DiemDonTra;
import com.example.BusTicketBookingBackend.service.DiemDonTraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Station")
public class DiemDonTraController {
    private final DiemDonTraService diemDonTraService;

    @GetMapping("/getByProvince")
    public ResponseEntity<ApiResponse<List<DiemDonTra>>> getDiemDonTraByProvince(@RequestParam String province) {
        ApiResponse<List<DiemDonTra>> apiResponse = ApiResponse.<List<DiemDonTra>>builder()
                .result(diemDonTraService.getDiemDonTraByTinhThanh(province))
                .code(200)
                .message("Success")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/id")
    public ApiResponse deleteDiemDonTraById(@RequestParam Integer id) {
        return ApiResponse.builder()
                .code(200)
                .message("Success")
                .result(diemDonTraService.deleteDiemDonTra(id))
                .build();
    }

    @PostMapping("/themDiemDon")
    public ApiResponse create(@RequestBody DiemDonTraRequest diemDonTraRequest) {
        return ApiResponse.builder()
                .code(200)
                .message("Success")
                .result(diemDonTraService.addDiemDonTra(diemDonTraRequest))
                .build();
    }



}
