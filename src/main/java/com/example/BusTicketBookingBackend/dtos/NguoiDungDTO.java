package com.example.BusTicketBookingBackend.dtos;

import com.example.BusTicketBookingBackend.models.NguoiDung;
import com.example.BusTicketBookingBackend.models.VaiTro;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NguoiDungDTO {
    private int id;
    private String hoTen;
    private String email;
    private String matKhau;
    private String SDT;
    private String trangThai;
    private String loaiDangKi;
    private String confirmToken;
    private String vaiTro;
}
