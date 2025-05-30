package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.dtos.request.ChuyenXeDTO;
import com.example.BusTicketBookingBackend.dtos.response.ChuyenXeResponse;
import com.example.BusTicketBookingBackend.dtos.response.DonDatVeResponse;
import com.example.BusTicketBookingBackend.enums.TrangThaiDonDat;
import com.example.BusTicketBookingBackend.models.ChuyenXe;
import com.example.BusTicketBookingBackend.models.TuyenXe;
import com.example.BusTicketBookingBackend.service.ChuyenXeService;
import com.example.BusTicketBookingBackend.service.DatGheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chuyenxe")
@RequiredArgsConstructor
public class ChuyenXeController {

    private final ChuyenXeService chuyenXeService;
    private final DatGheService datGheService;

    @GetMapping("/getAllChuyenXe")
    public ApiResponse<List<ChuyenXeResponse>> getAll() {
        ApiResponse apiResponse = new ApiResponse();
        List<ChuyenXeResponse> chuyenXeList = chuyenXeService.getAll();
        apiResponse.setResult(chuyenXeList);
        apiResponse.setMessage("ChuyenXe get all");
        apiResponse.setCode(HttpStatus.OK.value());
        return apiResponse;
    }

    @GetMapping("/search")
    public ResponseEntity<?> getChuyenXe(
            @RequestParam("idDiemDi") String idDiemDi,
            @RequestParam("idDiemDen") String idDiemDen,
            @RequestParam("ngayDi") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate ngayDi,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate ngayVe,
            @RequestParam(required = false, defaultValue = "false") boolean khuHoi) {

        try {
            int diTu = Integer.valueOf(idDiemDi);
            int denDiem = Integer.valueOf(idDiemDen);

            List<ChuyenXeResponse> dtoList = chuyenXeService.timChuyenXeTheoTuyen(diTu, denDiem, ngayDi, ngayVe, khuHoi);
            return ResponseEntity.ok(dtoList);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping("/TaoChuyenXe")
    public ApiResponse createChuyenXe(@RequestBody ChuyenXeDTO chuyenXedto) {
        return ApiResponse.builder()
                .code(200)
                .message("ChuyenXe create")
                .result(chuyenXeService.taoChuyenXe(chuyenXedto))
                .build();
    }

    @PutMapping("/editChuyenXe")
    public ApiResponse editChuyenXe(@RequestBody ChuyenXeDTO chuyenXedto, @RequestParam Integer id) {
        return ApiResponse.builder()
                .result(chuyenXeService.editChuyenXe(chuyenXedto, id))
        .code(200).message("Success").build();
    }

    @GetMapping("/danhSachDatGhe")
    public ApiResponse getDanhSachGhe(@RequestParam Integer id){
        return ApiResponse.builder()
                .result(datGheService.getByChuyenXe(id))
                .code(200)
                .message("Danh sach dat ghe cua chuyen xe ")
                .build();
    }

    @PostMapping("/kiemtra-ghe")
    public ResponseEntity<?> kiemTraTrangThaiGhe(@RequestBody Map<String, List<Integer>> request) {
        try {
            List<Integer> seatIds = request.get("seatIds");

            // Kiểm tra ghế
            boolean areSeatsAvailable = datGheService.seatSelectedIsAvaible(seatIds);

            if (areSeatsAvailable) {
                return ResponseEntity.ok(ApiResponse.builder()
                        .code(200)
                        .message("Ghế còn trống")
                        .build()
                );
            } else {
                return ResponseEntity.ok(ApiResponse.builder()
                        .code(400)
                        .message("Một số ghế đã được đặt")
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .code(500)
                    .message("Lỗi server: " + e.getMessage())
                    .build());
        }
    }

    @GetMapping("/lichTrinhChuyenXe")
    public ApiResponse getlichTrinhChuyenXe(@RequestParam Integer idChuyenXe){
        return ApiResponse.builder()
                .code(200)
                .message("Lịch trình của chuyến xe + {idChuyenXe}")
                .result(chuyenXeService.getLichTrinhChuyenXe(idChuyenXe))
                .build();
    }



    @DeleteMapping("/huyChuyen/{id}")
    public ApiResponse huyChuyenXe(@PathVariable Integer id){
        return ApiResponse.builder()
                .code(200)
                .message("Lịch trình của chuyến xe + {idChuyenXe}")
                .result(chuyenXeService.huyChuyen(id))
                .build();
    }

    @GetMapping("/getPage")
    public ResponseEntity<Page<ChuyenXeResponse>> getAllChuyenXeWithStatus(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam ChuyenXe.TrangThai trangThaiChuyenXe
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ngayKhoiHanh"));

        Page<ChuyenXeResponse> result = chuyenXeService.getAllChuyenXeByTrangThai(trangThaiChuyenXe,pageable );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/searchWithPageAndDate")
    public ResponseEntity<Page<ChuyenXeResponse>> searchChuyenXe(
            @RequestParam LocalDate keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam ChuyenXe.TrangThai trangThaiChuyenXe
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChuyenXeResponse> result = chuyenXeService.searchChuyenXe(keyword, pageable,trangThaiChuyenXe);
        return ResponseEntity.ok(result);
    }

}