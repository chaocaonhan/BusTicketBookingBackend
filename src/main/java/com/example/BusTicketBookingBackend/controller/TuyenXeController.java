package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.dtos.response.TuyenXeDTO;
import com.example.BusTicketBookingBackend.models.TuyenXe;
import com.example.BusTicketBookingBackend.service.TuyenXeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tuyen-xe")
@RequiredArgsConstructor
public class TuyenXeController {
    private final TuyenXeService tuyenXeService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<TuyenXe>>> getAllTuyenXe() {
        ApiResponse<List<TuyenXe>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(tuyenXeService.getAllTuyenXe());
        apiResponse.setCode(200);
        apiResponse.setMessage("Lấy thành công danh sách tuyến xe");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/suaThongTinTuyen/{id}")
    public ApiResponse upDateTuyenXe(@PathVariable Integer id, @RequestBody TuyenXeDTO tuyenXeDTO) {
        ApiResponse apiResponse = new ApiResponse();

        String result = tuyenXeService.editTuyenXe(tuyenXeDTO, id);

        if (result.startsWith("Không tìm thấy")) {
            apiResponse.setMessage(result);
        }

        return apiResponse;
    }

    @PostMapping("/taoTuyen")
    public ResponseEntity<?> createTuyenXe(@RequestBody TuyenXeDTO tuyenXeDTO) {
        try {
            TuyenXe newTuyenXe = tuyenXeService.createTuyenXe(tuyenXeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTuyenXe);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/xoaTuyen")
    public String deleteTuyenXe(@RequestParam Integer id) {
        return tuyenXeService.deleteTuyenXe(id);
    }

    @GetMapping("/top5-popular")
    public ApiResponse getTop5Popular(){
        return ApiResponse.builder()
                .code(200)
                .message("5 tuyến phổ biến nhất")
                .result(tuyenXeService.getTop5TuyenXePhoBien())
                .build();
    }


}
