package com.example.BusTicketBookingBackend.dtos.response;

import com.example.BusTicketBookingBackend.enums.TrangThaiVe;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.Timer;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VeXeResponse {
    int maVeXe;
    String tenTuyen;
    TrangThaiVe trangThaiVe;
    Integer giaVe;
    String loaiXe;
    String tenGhe;
    String trungChuyenTu;
    String diemBatDau;
    LocalTime thoiGianBatDau;
    String diemKetThuc;
    String bienSoXe;
    LocalTime thoiGianKetThuc;
    String trungChuyenDen;
}
