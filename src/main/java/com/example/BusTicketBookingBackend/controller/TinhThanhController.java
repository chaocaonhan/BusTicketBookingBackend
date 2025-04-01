package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.models.TinhThanh;
import com.example.BusTicketBookingBackend.service.TinhThanhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tinhthanh")
public class TinhThanhController {

    @Autowired
    private TinhThanhService tinhThanhService;

    @GetMapping("")
    public ApiResponse<List<TinhThanh>> getAllTinhThanh(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(tinhThanhService.getAllTinhThanh());
        return apiResponse;
    }

    @PostMapping("/createTinhThanh")
    public ApiResponse<TinhThanh> createTinhThanh(@RequestBody String tenTinh){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(200);
        apiResponse.setResult(tinhThanhService.createTinhThanh(tenTinh));
        return apiResponse;
    }

    @DeleteMapping("/deleteTinhThanh")
    public ApiResponse deleteTinhThanh(@RequestBody Integer id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(200);
        apiResponse.setMessage(tinhThanhService.deleteTinhThanh(id));
        return apiResponse;
    }
}
