package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.dtos.request.ChangePassRequest;
import com.example.BusTicketBookingBackend.dtos.response.ApiResponse;
import com.example.BusTicketBookingBackend.dtos.response.AuthResponseDTO;
import com.example.BusTicketBookingBackend.dtos.request.LoginDTO;
import com.example.BusTicketBookingBackend.dtos.response.NguoiDungDTO;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.NguoiDung;
import com.example.BusTicketBookingBackend.models.TaiXe;
import com.example.BusTicketBookingBackend.service.impl.CloudinaryService;
import com.example.BusTicketBookingBackend.service.NguoiDungService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/nguoidung")

@RequiredArgsConstructor
public class NguoiDungController {

    private final NguoiDungService nguoiDungService;
    private final RestClient.Builder builder;
    private final CloudinaryService cloudinaryService;

    @PostMapping("/register")
    //valid là để validation dữ liệu từ frontend gửi về
    public ResponseEntity<ApiResponse<NguoiDungDTO>> register(@RequestBody @Valid NguoiDungDTO nguoiDungDTO) {
        NguoiDungDTO result = nguoiDungService.createNguoiDung(nguoiDungDTO,1);

        if (result == null) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(result);
        apiResponse.setCode(HttpStatus.CREATED.value());
        apiResponse.setMessage("Đăng ký thành công! Vui lòng kiểm tra email để xác nhận tài khoản.");

        // Trả về response với status code 201 (CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
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

    @PostMapping("/createUser")
    public ApiResponse<NguoiDung> createUser(@RequestBody @Valid NguoiDungDTO nguoiDungDTO) {
        NguoiDungDTO nguoiDung = nguoiDungService.createNguoiDung(nguoiDungDTO,0);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(nguoiDungDTO);
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage("Tạo ng dùng thành công");
        return apiResponse;
    }


    @GetMapping("/myInfor")
    public ApiResponse<NguoiDungDTO> getMyInfor() {
        return ApiResponse.<NguoiDungDTO>builder()
                .code(200)
                .result(nguoiDungService.getMyInfor())
                .build();
    }

    @PutMapping("/changePassword/{id}")
    public ApiResponse changePass(@PathVariable Integer id,
                                  @RequestBody ChangePassRequest changePassRequest){
        return ApiResponse.builder()
                .code(200)
                .message(nguoiDungService.changePassword(id,changePassRequest))
                .build();
    };

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<NguoiDungDTO>>> getAllNoPaging() {
        ApiResponse<List<NguoiDungDTO>> response = ApiResponse.<List<NguoiDungDTO>>builder()
                .result(nguoiDungService.getAllNguoiDungNoPaging())
                .code(200)
                .message("Success")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getPage")
    public ResponseEntity<Page<NguoiDungDTO>> getAllNguoiDung(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NguoiDungDTO> result = nguoiDungService.getAllNguoiDung(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NguoiDungDTO>> searchNguoiDung(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NguoiDungDTO> result = nguoiDungService.searchNguoiDung(keyword, pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/danhSachTaiXe")
    public ApiResponse<List<TaiXe>> getDanhSachTaiXe() {
        return ApiResponse.<List<TaiXe>>builder()
                .result(nguoiDungService.getAllTaiXe())
                .code(200)
                .build();
    }

@PostMapping("/datLaiMatKhau")
public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestBody) {
    String email = requestBody.get("email");
    String response = nguoiDungService.forgotPassword(email);
    return new ResponseEntity<>(response, HttpStatus.OK);
}


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NguoiDungDTO>> updateUser(
            @PathVariable Integer id,
            @RequestBody NguoiDungDTO nguoiDungDTO) {

        NguoiDungDTO updatedUser = nguoiDungService.updateNguoiDung(id, nguoiDungDTO);

        ApiResponse<NguoiDungDTO> response = ApiResponse.<NguoiDungDTO>builder()
                .code(200)
                .message("Cập nhật thông tin người dùng thành công")
                .result(updatedUser)
                .build();

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
        boolean isDeleted = nguoiDungService.deleteNguoiDungById(id);

        if (isDeleted) {
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .code(200)
                    .message("User deleted successfully")
                    .result("User with ID " + id + " has been deleted")
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .code(404)
                    .message("User not found")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<ApiResponse<String>> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            // Sử dụng CloudinaryService để tải file ảnh lên Cloudinary
            String url = cloudinaryService.upload(file);

            // Lưu URL ảnh vào hồ sơ người dùng hiện tại (nếu cần)
            nguoiDungService.updateAvatarForCurrentUser(url);

            // Trả về URL ảnh đã được tải lên
            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .code(200)
                            .message("Cập nhật ảnh đại diện thành công!")
                            .result(url)
                            .build()
            );
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.<String>builder()
                            .code(500)
                            .message("Có lỗi xảy ra khi tải ảnh lên")
                            .build()
            );
        }
    }

}