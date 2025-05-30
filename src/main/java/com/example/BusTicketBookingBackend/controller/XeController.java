package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.dtos.response.XeDTO;
import com.example.BusTicketBookingBackend.models.LoaiXe;
import com.example.BusTicketBookingBackend.models.Xe;
import com.example.BusTicketBookingBackend.repositories.LoaiXeRepository;
import com.example.BusTicketBookingBackend.repositories.XeRepository;
import com.example.BusTicketBookingBackend.service.XeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Xe")
public class XeController {
    private final XeService xeService;
    private final XeRepository xeRepository;
    private final LoaiXeRepository loaiXeRepository;

    @GetMapping("/getAll")
    public ApiResponse<List<XeDTO>> getAll(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(200);
        apiResponse.setResult(xeService.getAllXe());
        return apiResponse;
    }

    @GetMapping("/getAllLoaiXe")
    public ApiResponse<List<LoaiXe>> getAllLoaiXe(){
        ApiResponse<List<LoaiXe>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(loaiXeRepository.findAll());
        return apiResponse;
    }

    @PutMapping("/suaThongTinXe/{id}")
    public ApiResponse suaThongTinXe(@PathVariable int id, @RequestBody XeDTO xeDTO){
        return ApiResponse.builder()
                .code(200)
                .message("suaThongTinXe")
                .result(xeService.suaThongTinXe(id, xeDTO))
                .build();
    }

    @PostMapping("/themXe")
    public ApiResponse themXe(@RequestBody XeDTO xeDTO){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(200);
        apiResponse.setMessage("Tao chuyen thanh cong");
        apiResponse.setResult(xeService.createXe(xeDTO));

        return apiResponse;
    }

    @DeleteMapping("/xoaXe/{id}")
    public ApiResponse xoaXe(@PathVariable int id){
        Xe xe = xeRepository.findById(id).get();
        if(xe == null){
            return ApiResponse.builder()
                    .code(404)
                    .message("xoaXe")
                    .build();
        }
        xe.setTrangThai(2);
        xeRepository.save(xe);
        return ApiResponse.builder()
                .code(200)
                .message("xoaXe")
                .build();
    }

}
