package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.request.CapNhatLichTrinhRequest;
import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.dtos.response.DiemDungTrenTuyenDTO;
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
    public ApiResponse<List<DiemDungTrenTuyenDTO>> getDiemDungByTuyen(@PathVariable Integer idTuyen) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(diemDungTrenTuyenService.danhSachDiemDungCuaMotTuyen(idTuyen));
        apiResponse.setMessage("Diem Dung Tren Tuyen");
        apiResponse.setCode(200);
        return apiResponse;
    }

    @PutMapping("/capNhatDiemDungTrenTuyen")
    public ApiResponse capNhatDanhSachDiemDonTraCuaTuyen(@RequestBody CapNhatLichTrinhRequest capNhatLichTrinhRequest) {
        return ApiResponse.builder()
                .code(200)
                .message("cap nhat diem dung tren tuyen")
                .result(diemDungTrenTuyenService.capnhat(capNhatLichTrinhRequest))
                .build();
    }
}
