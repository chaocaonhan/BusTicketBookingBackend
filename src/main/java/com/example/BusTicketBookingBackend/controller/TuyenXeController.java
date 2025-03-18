package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.TuyenXeDTO;
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
    public List<TuyenXe> getAllTuyenXe() {
        return tuyenXeService.getAllTuyenXe();
    }

    @PutMapping("/suaThongTinTuyen/{id}")
    public ResponseEntity<String> upDateTuyenXe(@PathVariable Integer id, @RequestBody TuyenXeDTO tuyenXeDTO) {
        String result = tuyenXeService.editTuyenXe(tuyenXeDTO, id);

        if (result.startsWith("Không tìm thấy")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.ok(result);
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


}
