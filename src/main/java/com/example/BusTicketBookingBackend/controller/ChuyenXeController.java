package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeDTO;
import com.example.BusTicketBookingBackend.dtos.response.ChuyenXeResponse;
import com.example.BusTicketBookingBackend.models.ChuyenXe;
import com.example.BusTicketBookingBackend.service.ChuyenXeService;
import com.example.BusTicketBookingBackend.service.DatGheService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chuyenxe")
@RequiredArgsConstructor
public class ChuyenXeController {

    private final ChuyenXeService chuyenXeService;
    private final DatGheService datGheService;

    @GetMapping("/getAllChuyenXe")
    public ApiResponse<List<ChuyenXeResponse>> getAll() {
        ApiResponse apiResponse = new ApiResponse();
        List<ChuyenXeResponse> chuyenXeList = chuyenXeService.getAll();
        apiResponse.setResult(chuyenXeList);
        apiResponse.setMessage("ChuyenXe get all");
        apiResponse.setCode(HttpStatus.OK.value());
        return apiResponse;
    }

    @GetMapping("/search")
    public ResponseEntity<?> getChuyenXe(
            @RequestParam("tinhDi") String tinhDi,
            @RequestParam("tinhDen") String tinhDen,
            @RequestParam("ngayDi") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate ngayDi,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate ngayVe,
            @RequestParam(required = false, defaultValue = "false") boolean khuHoi) {

        try {
            List<ChuyenXeResponse> dtoList = chuyenXeService.timChuyenXeTheoTuyen(tinhDi, tinhDen, ngayDi, ngayVe, khuHoi);
            return ResponseEntity.ok(dtoList);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping("/TaoChuyenXe")
    public String createChuyenXe(@RequestBody ChuyenXeDTO chuyenXedto) {
        return chuyenXeService.taoChuyenXe(chuyenXedto);
    }

    @PutMapping("/editChuyenXe")
    public ApiResponse editChuyenXe(@RequestBody ChuyenXeDTO chuyenXedto, @RequestParam Integer id) {
        return ApiResponse.builder()
                .result(chuyenXeService.editChuyenXe(chuyenXedto, id))
        .code(200).message("Success").build();
    }

    @GetMapping("/danhSachDatGhe")
    public ApiResponse getDanhSachGhe(@RequestParam Integer id){
        return ApiResponse.builder()
                .result(datGheService.getByChuyenXe(id))
                .code(200)
                .message("Danh sach dat ghe cua chuyen xe ")
                .build();
    }

    @PostMapping("/kiemtra-ghe")
    public ResponseEntity<?> kiemTraTrangThaiGhe(@RequestBody Map<String, List<Integer>> request) {
        try {
            List<Integer> seatIds = request.get("seatIds");

            // Kiểm tra ghế
            boolean areSeatsAvailable = datGheService.seatSelectedIsAvaible(seatIds);

            if (areSeatsAvailable) {
                return ResponseEntity.ok(ApiResponse.builder()
                        .code(200)
                        .message("Ghế còn trống")
                        .build()
                );
            } else {
                return ResponseEntity.ok(ApiResponse.builder()
                        .code(400)
                        .message("Một số ghế đã được đặt")
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .code(500)
                    .message("Lỗi server: " + e.getMessage())
                    .build());
        }
    }
}