package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.LoginDTO;
import com.example.BusTicketBookingBackend.dtos.NguoiDungDTO;
import com.example.BusTicketBookingBackend.models.DiemDungTrenTuyen;
import com.example.BusTicketBookingBackend.repositories.DiemDungTrenTuyenRepository;
import com.example.BusTicketBookingBackend.service.NguoiDungService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/nguoidung")

@RequiredArgsConstructor
public class NguoiDungController {

    private final NguoiDungService nguoiDungService;

    @GetMapping("")
    public List<NguoiDungDTO> getAllNguoiDung() {
        return nguoiDungService.getAllNguoiDung();
    }

    @PostMapping("/dangKy")
    public String createNguoiDung(@RequestBody NguoiDungDTO nguoiDungDTO) {
        return nguoiDungService.createNguoiDung(nguoiDungDTO);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(
            @RequestParam int userId,
            @RequestParam String confirmToken) {

        boolean isVerified = nguoiDungService.verifyUser(userId, confirmToken);

        if (isVerified) {
            return ResponseEntity.ok().body(Map.of("message", "Xác thực thành công!"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Mã xác nhận không hợp lệ hoặc đã hết hạn."));
        }
    }

    @PostMapping("/login")
    public String loginToken(@RequestBody LoginDTO loginDTO){
        return nguoiDungService.login(loginDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<NguoiDungDTO> getNguoiDungByID(@PathVariable int id) {
        return nguoiDungService.getNguoiDungByID(id)
                .map(ResponseEntity::ok)  // Nếu có dữ liệu -> trả về 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Nếu không có -> trả về 404
    }

    @PutMapping("/suaNguoiDung")
    public String editNguoiDung(@RequestBody NguoiDungDTO nguoiDungDTO) {
        return nguoiDungService.updateNguoiDung(nguoiDungDTO);
    }





}