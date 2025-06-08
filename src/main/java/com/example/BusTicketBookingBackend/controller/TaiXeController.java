package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.dtos.response.LichLamViecResponse;
import com.example.BusTicketBookingBackend.models.TaiXe;
import com.example.BusTicketBookingBackend.service.TaiXeService;
import com.example.BusTicketBookingBackend.service.impl.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/taiXe")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaiXeController {
    TaiXeService taiXeService;
    CloudinaryService cloudinaryService;

    @GetMapping("/taiXeTrongLich/{ngayKhoiHanh}/{gioKhoiHanh}")
    public ApiResponse getAvailableTaiXeTrongLich(@PathVariable LocalDate ngayKhoiHanh,
                                                  @PathVariable LocalTime gioKhoiHanh) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(200);
        apiResponse.setMessage("TaiXe trong lich");
        apiResponse.setResult(taiXeService.getAvailableTaiXe(ngayKhoiHanh, gioKhoiHanh));

        return apiResponse;
    }

    @GetMapping("/getAll")
    public ApiResponse getAll() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(200);
        apiResponse.setMessage("Success");
        apiResponse.setResult(taiXeService.getAllTaiXe());
        return apiResponse;
    }

    @PostMapping("/themAnh")
    public ResponseEntity<ApiResponse<String>> themAnh(
            @RequestParam("id") Integer id,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String url = cloudinaryService.upload(file);

            taiXeService.updateAnh(url,id);

            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .code(200)
                            .message("Thêm ảnh thành công!")
                            .result(url)
                            .build()
            );
        } catch (IOException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.<String>builder()
                            .code(500)
                            .message("Có lỗi xảy ra khi thêm ảnh")
                            .build()
            );
        }
    }

    @GetMapping("/{idTaiXe}/lich-lam-viec")
    public ApiResponse<List<LichLamViecResponse>> lichLamViec(
            @PathVariable("idTaiXe") Integer idTaiXe,
            @RequestParam("ngayYeuCau")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate ngayYeuCau
    ) {
        List<LichLamViecResponse> lich = taiXeService.getLichLamViec(idTaiXe, ngayYeuCau);
        return ApiResponse.<List<LichLamViecResponse>>builder()
                .code(200)
                .message("Success")
                .result(lich)
                .build();
    }


}
