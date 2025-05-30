package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.models.TaiXe;
import com.example.BusTicketBookingBackend.service.TaiXeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/taiXe")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaiXeController {
    TaiXeService taiXeService;

    @GetMapping("/taiXeTrongLich/{ngayKhoiHanh}/{gioKhoiHanh}")
    public ApiResponse getAvailableTaiXeTrongLich(@PathVariable LocalDate ngayKhoiHanh,
                                                  @PathVariable LocalTime gioKhoiHanh) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(200);
        apiResponse.setMessage("TaiXe trong lich");
        apiResponse.setResult(taiXeService.getAvailableTaiXe(ngayKhoiHanh, gioKhoiHanh));

        return apiResponse;
    }



}
