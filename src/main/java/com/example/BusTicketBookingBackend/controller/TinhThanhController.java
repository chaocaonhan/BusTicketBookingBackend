package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.models.TinhThanh;
import com.example.BusTicketBookingBackend.service.CloudinaryService;
import com.example.BusTicketBookingBackend.service.TinhThanhService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tinhthanh")
public class TinhThanhController {

    private static final Logger log = LoggerFactory.getLogger(TinhThanhController.class);
    @Autowired
    private final TinhThanhService tinhThanhService;
    private final CloudinaryService cloudinaryService;

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

    @PostMapping("/themAnh")
    public ResponseEntity<ApiResponse<String>> themAnh(
            @RequestParam("id") Integer id,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String url = cloudinaryService.upload(file);

            // Cập nhật ảnh của tỉnh thành trong cơ sở dữ liệu
            tinhThanhService.updateAnh(url,id);

            // Trả về phản hồi thành công
            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .code(200)
                            .message("Thêm ảnh thành công!")
                            .result(url)
                            .build()
            );
        } catch (IOException e) {
            // Xử lý lỗi tải ảnh lên
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.<String>builder()
                            .code(500)
                            .message("Có lỗi xảy ra khi thêm ảnh")
                            .build()
            );
        }
    }

}
