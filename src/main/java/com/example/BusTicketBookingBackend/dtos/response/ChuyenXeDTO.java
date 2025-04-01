package com.example.BusTicketBookingBackend.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChuyenXeDTO {
    String tenTuyen;
    String bienSoXe;
    String taiXe;
    String diemDi;
    String diemDen;
    LocalDate ngayKhoiHanh;
    LocalTime gioKhoiHanh;
    LocalTime gioKetThuc;
    int giaVe;
}
