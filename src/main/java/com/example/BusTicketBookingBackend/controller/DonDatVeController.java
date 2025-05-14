package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.request.DatVeRequest;
import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.models.DonDatVe;
import com.example.BusTicketBookingBackend.service.DonDatVeService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/datve")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DonDatVeController {
    DonDatVeService donDatVeService;

    @PostMapping("")
    public ApiResponse taoDonDatVe(@RequestBody DatVeRequest request) {
        return ApiResponse.builder()
                .code(200)
                .message("Success")
                .result(donDatVeService.taoDonDatVe(request)).build();
    }

    @GetMapping("/getAllDonDat")
    public ApiResponse danhSachDonDat(){
        return ApiResponse.builder()
                .code(200)
                .message("Success")
                .result(donDatVeService.getAllDonDat()).build();
    }

    @GetMapping("/getMyDonDat")
    public ApiResponse getMyDonDat(){
        return ApiResponse.builder()
                .code(200)
                .message("Success")
                .result(donDatVeService.getMyBooking()).build();
    }
}
