package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.models.TinhThanh;
import com.example.BusTicketBookingBackend.service.TinhThanhService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tinhthanh")
public class TinhThanhController {

    private static final Logger log = LoggerFactory.getLogger(TinhThanhController.class);
    @Autowired
    private TinhThanhService tinhThanhService;

    @GetMapping("")
    public ApiResponse<List<TinhThanh>> getAllTinhThanh(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("user name:{}",authentication.getName());
        log.info("Role:{}",authentication.getAuthorities());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(tinhThanhService.getAllTinhThanh());
        apiResponse.setCode(200);
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
