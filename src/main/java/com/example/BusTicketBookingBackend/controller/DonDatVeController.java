package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.request.DatVeRequest;
import com.example.BusTicketBookingBackend.dtos.request.FindingRequest;
import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.dtos.response.DonDatVeResponse;
import com.example.BusTicketBookingBackend.enums.TrangThaiDonDat;
import com.example.BusTicketBookingBackend.service.DonDatVeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/getMyDonDatByTrangThai")
    public ResponseEntity<Page<DonDatVeResponse>> getMyDonDat(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam TrangThaiDonDat trangThaiDonDat
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "thoiGianDat"));

        Page<DonDatVeResponse> donDatCuaToi = donDatVeService.getMyDonDatVeByTrangThai(trangThaiDonDat,pageable );
        return ResponseEntity.ok(donDatCuaToi);
    }

    @DeleteMapping("/huyDon/{id}")
    public ApiResponse getMyDonDat(@PathVariable("id") Integer idDonCanHuy){
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
            result = "Không tìm thấy";
        } else {
            trangThai = 200;
            result = kqTimKiem.get();
        }

        return ApiResponse.builder()
                .code(trangThai)
                .message("Success")
                .result(result)
                .build();
    }

    @GetMapping("/findByChuyenXe/{idChuyenXe}")
    public ApiResponse findByChuyenXe(@PathVariable Integer idChuyenXe) {
        return ApiResponse.builder()
                .code(200)
                .message("Success")
                .result(donDatVeService.getDonDatVeByIdChuyenXe(idChuyenXe))
                .build();

    }

    @GetMapping("/getPage")
    public ResponseEntity<Page<DonDatVeResponse>> getAllDonDatVe(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam TrangThaiDonDat trangThaiDonDat
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "thoiGianDat"));

        Page<DonDatVeResponse> result = donDatVeService.getAllDonDatVeByTrangThai(trangThaiDonDat,pageable );
        return ResponseEntity.ok(result);
    }


    @GetMapping("/search")
    public ResponseEntity<Page<DonDatVeResponse>> searchDonDatVe(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam TrangThaiDonDat trangThaiDonDat
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DonDatVeResponse> result = donDatVeService.searchDonDatVe(keyword, pageable,trangThaiDonDat);
        return ResponseEntity.ok(result);
    }

}
