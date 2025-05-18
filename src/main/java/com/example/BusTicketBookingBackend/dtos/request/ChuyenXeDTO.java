package com.example.BusTicketBookingBackend.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "HH:mm")
    LocalTime gioKhoiHanh;

    @JsonFormat(pattern = "HH:mm")
    LocalTime gioKetThuc;
    int giaVe;
}
