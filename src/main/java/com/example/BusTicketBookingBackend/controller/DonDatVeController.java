package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.request.DatVeRequest;
import com.example.BusTicketBookingBackend.dtos.request.FindingRequest;
import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.dtos.response.DonDatVeResponse;
import com.example.BusTicketBookingBackend.models.DonDatVe;
import com.example.BusTicketBookingBackend.service.DonDatVeService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @DeleteMapping("/huyDon/{id}")
    public ApiResponse getMyDonDat(@PathVariable Integer idDonCanHuy){
        donDatVeService.huyDon(idDonCanHuy);
        return ApiResponse.builder()
                .code(200)
                .message("Đã huỷ đơn")
                .build();
    }

    @PostMapping("/findByIdAndPhoneNumber")
    public ApiResponse finding(@RequestBody FindingRequest request) {
        Optional<DonDatVeResponse> kqTimKiem = donDatVeService.traCuuDonDat(request);
        int trangThai;
        Object result;

        if (kqTimKiem.isEmpty()) {
            trangThai = 404;
            result = "No booking found for the provided details"; // Provide a meaningful message for empty results
        } else {
            trangThai = 200;
            result = kqTimKiem.get(); // Safe because this block guarantees that the Optional is not empty
        }

        return ApiResponse.builder()
                .code(trangThai)
                .message("Success")
                .result(result)
                .build();
    }

}
