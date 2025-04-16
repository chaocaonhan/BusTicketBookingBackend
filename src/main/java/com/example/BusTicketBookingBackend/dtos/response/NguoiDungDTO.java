package com.example.BusTicketBookingBackend.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NguoiDungDTO {
    int id;
    String hoTen;

    String gioiTinh;

    @Email(message = "EMAIL_INVALID")
//    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email phải là @gmail.com")
    String email;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String matKhau;
    String SDT;
    String trangThai;
    String loaiDangKi;
    String vaiTro;
}
