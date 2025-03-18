package com.example.BusTicketBookingBackend.dtos;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChuyenXeSearchDTO {
    private String diemDi;
    private String diemDen;
    private LocalTime gioKhoiHanh;
    private LocalTime gioKetThuc;
    private int giaVe;
    private String tenLoaiXe;
    private int  soGheTrong;
}
