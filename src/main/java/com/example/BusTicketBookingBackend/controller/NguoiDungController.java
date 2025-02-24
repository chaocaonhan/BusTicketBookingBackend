package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.NguoiDungDTO;
import com.example.BusTicketBookingBackend.service.NguoiDungService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/nguoidung")

@RequiredArgsConstructor
public class NguoiDungController {
    private final NguoiDungService nguoiDungService;

    @GetMapping("")
    public List<NguoiDungDTO> getAllNguoiDung() {
        return nguoiDungService.getAllNguoiDung();
    }
}