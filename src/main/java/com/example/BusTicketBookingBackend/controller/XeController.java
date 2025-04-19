package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.dtos.response.XeDTO;
import com.example.BusTicketBookingBackend.models.LoaiXe;
import com.example.BusTicketBookingBackend.repositories.LoaiXeRepository;
import com.example.BusTicketBookingBackend.repositories.XeRepository;
import com.example.BusTicketBookingBackend.service.XeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
