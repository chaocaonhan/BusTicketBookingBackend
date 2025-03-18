package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.models.TinhThanh;
import com.example.BusTicketBookingBackend.service.TinhThanhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tinhthanh")
public class TinhThanhController {

    @Autowired
    private TinhThanhService tinhThanhService;

    @GetMapping("")
    public List<TinhThanh> getAllTinhThanh(){
        return tinhThanhService.getAllTinhThanh();
    }

    @PostMapping("/createTinhThanh")
    public TinhThanh createTinhThanh(@RequestBody TinhThanh tinh){
        return tinhThanhService.createTinhThanh(tinh.getTenTinhThanh());
    }

    @DeleteMapping("/deleteTinhThanh")
    public String deleteTinhThanh(@RequestBody String tinh){
        return tinhThanhService.deleteTinhThanh(tinh);
    }
}
