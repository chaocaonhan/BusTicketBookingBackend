package com.example.BusTicketBookingBackend.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonDatVeResponse {
    int id;
    String tenNguoiDat;
    LocalDateTime ngayDat;
    int tongTien;
    String kieuThanhToan;
    String trangThaiThanhToan;
    int soLuongVe;
    String ghiChu;
    String tenHanhKhach;
    String SDT;
    String email;

}
