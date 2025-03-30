package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.AuthResponseDTO;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody NguoiDungDTO nguoiDungDTO) {
        String result = nguoiDungService.createNguoiDung(nguoiDungDTO);

        if (result.equals("Email đã tồn tại")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else if (result.startsWith("Tài khoản đã được tạo")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            try {
                int userId = Integer.parseInt(result);
                return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDTO(userId, "Đăng ký thành công! Vui lòng kiểm tra email để xác nhận tài khoản."));
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        String result = nguoiDungService.login(loginDTO);

        switch (result) {
            case "NULL":
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
            case "PASSWORD":
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mật khẩu không đúng");
            case "LOCK_OR_VERIFY":
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Tài khoản chưa được xác nhận hoặc đã bị khóa");
            case "AUTH_ERROR":
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi xác thực");
            default:
                if (result.startsWith("OTHER_CASE")) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi không xác định");
                } else {
                    // Trả về token JWT
                    return ResponseEntity.ok(new AuthResponseDTO(result, "Đăng nhập thành công"));
                }
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam("userId") Integer userId, @RequestParam("token") String token) {
        Boolean isVerified = nguoiDungService.verifyUser(userId, token);

        if (isVerified) {
            return ResponseEntity.ok("Xác nhận tài khoản thành công. Bạn có thể đăng nhập ngay bây giờ.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Xác nhận tài khoản thất bại. Mã xác nhận không hợp lệ hoặc đã hết hạn.");
        }
    }





}