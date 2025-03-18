package com.example.BusTicketBookingBackend.dtos;

import com.example.BusTicketBookingBackend.models.DiemDonTra;
import com.example.BusTicketBookingBackend.models.TaiXe;
import com.example.BusTicketBookingBackend.models.TuyenXe;
import com.example.BusTicketBookingBackend.models.Xe;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChuyenXeDTO {
    private String tenTuyen;
    private String bienSoXe;
    private String taiXe;
    private String diemDi;
    private String diemDen;
    private LocalDate ngayKhoiHanh;
    private LocalTime gioKhoiHanh;
    private LocalTime gioKetThuc;
    private int giaVe;
}
