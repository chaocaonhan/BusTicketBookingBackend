package com.example.BusTicketBookingBackend.controller;


import com.example.BusTicketBookingBackend.dtos.request.KhuyenMaiDTO;
import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.repositories.KhuyenMaiRepository;
import com.example.BusTicketBookingBackend.service.KhuyenMaiService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/khuyen-mai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KhuyenMaiController {
    KhuyenMaiService khuyenMaiService;
    private final KhuyenMaiRepository khuyenMaiRepository;

    @GetMapping("/check")
    public ApiResponse checkKm(@RequestParam String maKm){
        return ApiResponse.builder()
                .code(khuyenMaiService.checkKhuyenMai(maKm) == 0 ? 404 : 200)
                .message(khuyenMaiService.checkKhuyenMai(maKm) == 0 ? "K hợp lệ" : "Áp mã thành công")
                .result(khuyenMaiService.checkKhuyenMai(maKm))
                .build();
    }

    @GetMapping("/getAll")
    public ApiResponse getAllKm(){
        return ApiResponse.builder()
                .code(200)
                .result(khuyenMaiRepository.findAll())
                .build();
    }

    @PostMapping("/addMa")
    public ApiResponse addKM(@RequestBody KhuyenMaiDTO kmDTO){
        return ApiResponse.builder()
                .code(200)
                .message("Add thành công")
                .result(khuyenMaiService.createKhuyenMai(kmDTO))
                .build();
    }
}
